package de.ninafoss.domain.usecases.message;

import de.ninafoss.domain.Message;
import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.MessageRepository;
import de.ninafoss.generator.Parameter;
import de.ninafoss.generator.UseCase;

@UseCase
class SaveMessage {

	private final MessageRepository messageRepository;
	private final Message message;

	public SaveMessage(MessageRepository messageRepository, @Parameter Message message) {
		this.messageRepository = messageRepository;
		this.message = message;
	}

	public Message execute() throws BackendException {
		return messageRepository.store(message);
	}

}
