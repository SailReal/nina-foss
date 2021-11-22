package de.ninafoss.presentation.workflow;

import de.ninafoss.generator.BoundCallback;

import java.io.Serializable;

class SerializableResult<T extends Serializable> extends AsyncResult {

	private final T result;

	SerializableResult(BoundCallback callback, T result) {
		super(callback);
		this.result = result;
	}

	public T getResult() {
		return result;
	}

}
