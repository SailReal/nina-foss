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
				.withMessage(entity.getMessage()) //
				.withLocation(locationFrom(entity)) //
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
		entity.setMessage(domainObject.getMessage());
		if (domainObject.getLocation() != null) {
			entity.setLocation(locationEntityMapper.toEntity(domainObject.getLocation()));
		}
		return entity;
	}
}
