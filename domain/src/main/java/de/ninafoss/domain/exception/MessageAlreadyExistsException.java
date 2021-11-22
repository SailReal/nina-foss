package de.ninafoss.domain.exception;

public class MessageAlreadyExistsException extends FatalBackendException {

	public MessageAlreadyExistsException(Throwable e) {
		super(e);
	}

	public MessageAlreadyExistsException(String message, Throwable e) {
		super(message, e);
	}

	public MessageAlreadyExistsException(String message) {
		super(message);
	}

	public MessageAlreadyExistsException() {
		super("");
	}

}
