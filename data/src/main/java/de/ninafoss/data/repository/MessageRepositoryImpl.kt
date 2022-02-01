package de.ninafoss.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import de.ninafoss.data.db.Database
import de.ninafoss.data.db.entities.LocationEntity
import de.ninafoss.data.db.entities.MessageEntity
import de.ninafoss.data.db.mappers.LocationEntityMapper
import de.ninafoss.data.db.mappers.MessageEntityMapper
import de.ninafoss.data.repository.messagedetails.MessageDetailsDTO
import de.ninafoss.data.repository.messageoverview.MessageOverviewDTO
import de.ninafoss.data.util.UserAgentInterceptor
import de.ninafoss.domain.Message
import de.ninafoss.domain.exception.BackendException
import de.ninafoss.domain.exception.FatalBackendException
import de.ninafoss.domain.exception.MessageAlreadyExistsException
import de.ninafoss.domain.repository.MessageRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MessageRepositoryImpl @Inject constructor(
	private val messageEntityMapper: MessageEntityMapper,
	private val locationEntityMapper: LocationEntityMapper,
	private val database: Database
) : MessageRepository {

	private val httpClient: OkHttpClient

	@Throws(BackendException::class)
	override fun messages(): List<Message> {
		return messageEntityMapper.fromEntities(database.loadAll(MessageEntity::class.java))
	}

	@Throws(BackendException::class)
	override fun store(message: Message): Message {
		return try {
			messageEntityMapper.fromEntity(database.store(messageEntityMapper.toEntity(message)))
		} catch (e: SQLiteConstraintException) {
			throw MessageAlreadyExistsException()
		}
	}

	@Throws(BackendException::class)
	override fun delete(message: Message): Long {
		database.delete(messageEntityMapper.toEntity(message))
		return message.id
	}

	@Throws(BackendException::class)
	override fun load(id: Long): Message {
		val message = messageEntityMapper.fromEntity(database.load(MessageEntity::class.java, id))
		return Message.aCopyOf(message).build()
	}

	@Throws(BackendException::class)
	override fun updateMessagesForAllLocations(): List<Message> {
		val storedMessages = messages()

		val newOrUpdatedMessages = database.loadAll(LocationEntity::class.java).flatMap { location ->
			val newOrUpdatedMessages = getMessagesOverviewFor(location)
			// remove old messages
			storedMessages.filter { message -> newOrUpdatedMessages.find { newOrUpdated -> message.remoteId == newOrUpdated.id } == null }.map { delete(it) }
			// add new or updates messages
			newOrUpdatedMessages.filter { messageDTO ->
				storedMessages.find { storedMessage -> messageDTO.id == storedMessage.remoteId && messageDTO.payload.version == storedMessage.remoteVersion } == null
			}.map { messageOverviewDTO ->
				val messageDetails = getMessageDetailsFor(messageOverviewDTO)
				val messageStoredButUpdateRequired = storedMessages.find { stored -> stored.remoteId == messageDetails.identifier }
				val message = toMessage(messageOverviewDTO, messageDetails, messageStoredButUpdateRequired, location)
				store(message)
			}
		}

		return newOrUpdatedMessages
	}

	private fun getMessagesOverviewFor(location: LocationEntity): List<MessageOverviewDTO> {
		val request = Request.Builder() //
			.url("https://warnung.bund.de/api31/dashboard/${location.code}.json")
			.build()
		return try {
			val response = httpClient.newCall(request).execute()
			if (response.isSuccessful) {
				response.body?.let {
					val objectMapper = ObjectMapper()
					objectMapper.readValue(it.string(), object : TypeReference<List<MessageOverviewDTO>>() {})
				} ?: throw FatalBackendException("Failed to retrieve locations, response body is empty")
			} else {
				throw FatalBackendException("Failed to retrieve locations, response wasn't successful")
			}
		} catch (e: IOException) {
			throw FatalBackendException(e)
		} catch (e: JSONException) {
			throw FatalBackendException(e)
		}
	}

	private fun getMessageDetailsFor(messageOverviewDTO: MessageOverviewDTO): MessageDetailsDTO {
		val request = Request.Builder() //
			.url("https://warnung.bund.de/api31/warnings/${messageOverviewDTO.id}.json")
			.build()
		return try {
			val response = httpClient.newCall(request).execute()
			if (response.isSuccessful) {
				response.body?.use {
					val objectMapper = ObjectMapper()
					objectMapper.readValue(it.string(), object : TypeReference<MessageDetailsDTO>() {})
				} ?: throw FatalBackendException("Failed to retrieve locations, response body is empty")
			} else {
				throw FatalBackendException("Failed to retrieve locations, response wasn't successful")
			}
		} catch (e: IOException) {
			throw FatalBackendException(e)
		} catch (e: JSONException) {
			throw FatalBackendException(e)
		}
	}

	private fun toMessage(messageOverviewDTO: MessageOverviewDTO, messageDetailsDTO: MessageDetailsDTO, oldMessage: Message?, location: LocationEntity): Message {
		val info = messageDetailsDTO.info[0]
		val messageBuilder = Message.aMessage()
			.withRemoteId(messageDetailsDTO.identifier)
			.withRemoveVersion(messageOverviewDTO.payload.version)
			.withLocation(locationEntityMapper.fromEntity(location))
			.withHeadline(info.headline)
			.withMessage(info.description)
			.withInstruction(info.instruction)
			.withContact(info.contact)
			.withMessageType(messageOverviewDTO.payload.type)
			.withProvider(messageOverviewDTO.payload.data.provider)
			.withSentDate(messageOverviewDTO.sent)
			.withSeverity(info.severity)
		oldMessage?.let {
			messageBuilder.withId(it.id)
		} ?: messageBuilder.thatIsNew()
		return messageBuilder.build()
	}

	private fun httpClient(): OkHttpClient {
		return OkHttpClient.Builder().addInterceptor(UserAgentInterceptor()).build()
	}

	init {
		httpClient = httpClient()
	}
}
