package de.ninafoss.data.repository;

import static de.ninafoss.domain.Message.aCopyOf;

import android.database.sqlite.SQLiteConstraintException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ninafoss.data.db.Database;
import de.ninafoss.data.db.entities.MessageEntity;
import de.ninafoss.data.db.mappers.MessageEntityMapper;
import de.ninafoss.domain.Message;
import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.exception.MessageAlreadyExistsException;
import de.ninafoss.domain.repository.MessageRepository;

@Singleton
class MessageRepositoryImpl implements MessageRepository {

	private final Database database;
	private final MessageEntityMapper mapper;

	@Inject
	public MessageRepositoryImpl(MessageEntityMapper mapper, Database database) {
		this.mapper = mapper;
		this.database = database;
	}

	@Override
	public List<Message> messages() throws BackendException {
		List<Message> result = new ArrayList<>();
		for (Message message : mapper.fromEntities(database.loadAll(MessageEntity.class))) {
			result.add(aCopyOf(message).build());
		}
		return result;
	}

	@Override
	public Message store(Message message) throws BackendException {
		try {
			return mapper.fromEntity(database.store(mapper.toEntity(message)));
		} catch (SQLiteConstraintException e) {
			throw new MessageAlreadyExistsException();
		}
	}

	@Override
	public Long delete(Message message) throws BackendException {
		database.delete(mapper.toEntity(message));
		return message.getId();
	}

	@Override
	public Message load(Long id) throws BackendException {
		Message message = mapper.fromEntity(database.load(MessageEntity.class, id));
		return aCopyOf(message).build();
	}
}
