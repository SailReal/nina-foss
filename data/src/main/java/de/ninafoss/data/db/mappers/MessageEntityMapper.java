package de.ninafoss.data.db.mappers;

import static de.ninafoss.domain.Message.aMessage;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ninafoss.data.db.entities.MessageEntity;
import de.ninafoss.domain.Location;
import de.ninafoss.domain.Message;
import de.ninafoss.domain.exception.BackendException;

@Singleton
public class MessageEntityMapper extends EntityMapper<MessageEntity, Message> {

	private final LocationEntityMapper locationEntityMapper;

	@Inject
	public MessageEntityMapper(LocationEntityMapper locationEntityMapper) {
		this.locationEntityMapper = locationEntityMapper;
	}

	@Override
	public Message fromEntity(MessageEntity entity) throws BackendException {
		return aMessage() //
				.withId(entity.getId()) //
				.withRemoteId(entity.getRemoteId()) //
				.withRemoveVersion(entity.getRemoteVersion()) //
				.withLocation(locationFrom(entity)) //
				.withRemoveVersion(entity.getRemoteVersion()) //
				.withInstruction(entity.getInstruction()) //
				.withSeverity(entity.getSeverity()) //
				.withContact(entity.getContact()) //
				.withHeadline(entity.getHeadline()) //
				.withMessage(entity.getMessage()) //
				.withProvider(entity.getProvider()) //
				.withContact(entity.getContact()) //
				.withSentDate(entity.getSentDate()) //
				.build();
	}

	private Location locationFrom(MessageEntity entity) {
		if (entity.getLocation() == null) {
			return null;
		}
		return locationEntityMapper.fromEntity(entity.getLocation());
	}

	@Override
	public MessageEntity toEntity(de.ninafoss.domain.Message domainObject) {
		MessageEntity entity = new MessageEntity();
		entity.setId(domainObject.getId());
		entity.setRemoteId(domainObject.getRemoteId());
		if (domainObject.getLocation() != null) {
			entity.setLocation(locationEntityMapper.toEntity(domainObject.getLocation()));
		}
		entity.setRemoteVersion(domainObject.getRemoteVersion());
		entity.setInstruction(domainObject.getInstruction());
		entity.setSeverity(domainObject.getSeverity());
		entity.setContact(domainObject.getContact());
		entity.setHeadline(domainObject.getHeadline());
		entity.setMessage(domainObject.getMessage());
		entity.setProvider(domainObject.getProvider());
		entity.setMsgType(domainObject.getMsgType());
		entity.setSentDate(domainObject.getSentDate());
		return entity;
	}
}
