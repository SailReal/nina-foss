package de.ninafoss.domain.exception;

public class GeneralUpdateErrorException extends FatalBackendException {

	public GeneralUpdateErrorException(Throwable e) {
		super(e);
	}

	public GeneralUpdateErrorException(String message, Throwable e) {
		super(message, e);
	}

	public GeneralUpdateErrorException(String message) {
		super(message);
	}

	public GeneralUpdateErrorException() {
		super("");
	}

}
