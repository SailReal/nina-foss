package de.ninafoss.presentation.workflow;

import de.ninafoss.generator.BoundCallback;

public abstract class AsyncResult {

	private final BoundCallback callback;

	AsyncResult(BoundCallback callback) {
		this.callback = callback;
	}

	public BoundCallback callback() {
		return callback;
	}

}
