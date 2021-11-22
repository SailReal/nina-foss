package de.ninafoss.presentation.workflow;

import de.ninafoss.generator.BoundCallback;

public class PermissionsResult extends AsyncResult {

	private final boolean granted;

	public PermissionsResult(BoundCallback callback, boolean granted) {
		super(callback);
		this.granted = granted;
	}

	public boolean granted() {
		return granted;
	}

}
