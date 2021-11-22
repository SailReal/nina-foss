package de.ninafoss.domain.usecases.message;

import de.ninafoss.domain.Location;
import de.ninafoss.domain.Message;
import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.LocationRepository;
import de.ninafoss.domain.repository.MessageRepository;
import de.ninafoss.generator.Parameter;
import de.ninafoss.generator.UseCase;

@UseCase
class CreateMessage {

	private final LocationRepository locationRepository;
	private final MessageRepository messageRepository;
	private final Location location;
	private final String message;

	public CreateMessage(LocationRepository locationRepository, MessageRepository messageRepository, @Parameter Location location, @Parameter String message) {
		this.messageRepository = messageRepository;
		this.locationRepository = locationRepository;
		this.location = location;
		this.message = message;
	}

	public Message execute() throws BackendException {
		// locationRepository.create(location.code()); TODO why?
		return messageRepository.store(Message.aMessage() //
				.thatIsNew() //
				.withMessage(message) //
				.withLocation(location)
				.build());
	}
}
