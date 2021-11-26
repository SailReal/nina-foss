package de.ninafoss.domain.repository;

import java.util.List;

import de.ninafoss.domain.Message;
import de.ninafoss.domain.exception.BackendException;

public interface MessageRepository {

	List<Message> messages() throws BackendException;

	Message store(Message message) throws BackendException;

	Long delete(Message message) throws BackendException;

	Message load(Long id) throws BackendException;

	List<Message> updateMessagesForAllLocations() throws BackendException;

}
