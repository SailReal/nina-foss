package de.ninafoss.data.repository

import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton
import de.ninafoss.data.db.Database
import de.ninafoss.data.db.entities.LocationEntity
import de.ninafoss.data.db.mappers.LocationEntityMapper
import de.ninafoss.data.util.UserAgentInterceptor
import de.ninafoss.domain.Location
import de.ninafoss.domain.exception.BackendException
import de.ninafoss.domain.exception.FatalBackendException
import de.ninafoss.domain.repository.LocationRepository
import okhttp3.OkHttpClient
import okhttp3.Request

@Singleton
internal class LocationRepositoryImpl @Inject constructor(private val mapper: LocationEntityMapper, private val database: Database) : LocationRepository {

	private val httpClient: OkHttpClient

	@Throws(BackendException::class)
	override fun locations(): List<Location> {
		return mapper.fromEntities(database.loadAll(LocationEntity::class.java))
	}

	@Throws(BackendException::class)
	override fun allPossibleLocations(): Map<String, String> {
		val request: Request = Request.Builder() //
			.url("https://www.xrepository.de/api/xrepository/urn:de:bund:destatis:bevoelkerungsstatistik:schluessel:rs_2021-07-31/download/Regionalschl_ssel_2021-07-31.json")
			.build()
		try {
			val response = httpClient.newCall(request).execute()
			if (response.isSuccessful) {
				response.body?.let {
					val data = JSONObject(it.string()).getJSONArray("daten")
					val locations = HashMap<String, String>()
					for (i in 0 until data.length()) {
						val entry = data.getJSONArray(i)
						locations[entry.getString(1)] = entry.getString(0)
					}
					return locations
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

	override fun store(location: Location): Location {
		val storedLocation = mapper.fromEntity(database.store(mapper.toEntity(location)))
		database.clearCache()
		return storedLocation
	}

	override fun delete(location: Location) {
		database.delete(mapper.toEntity(location))
	}

	private fun httpClient(): OkHttpClient {
		return OkHttpClient.Builder().addInterceptor(UserAgentInterceptor()).build()
	}

	init {
		httpClient = httpClient()
	}
}
