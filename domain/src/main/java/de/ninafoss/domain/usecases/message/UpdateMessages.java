package de.ninafoss.domain.usecases.message;

import java.util.List;

import de.ninafoss.domain.Message;
import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.MessageRepository;
import de.ninafoss.generator.UseCase;

@UseCase
class UpdateMessages {

	private final MessageRepository messageRepository;

	public UpdateMessages(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public List<Message> execute() throws BackendException {
		return messageRepository.updateMessagesForAllLocations();
	}

}
