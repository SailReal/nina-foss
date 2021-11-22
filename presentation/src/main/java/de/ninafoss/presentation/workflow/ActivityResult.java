package de.ninafoss.presentation.workflow;

import static de.ninafoss.presentation.presenter.Presenter.SINGLE_RESULT;

import android.content.Intent;

import java.io.Serializable;

import de.ninafoss.generator.BoundCallback;

public class ActivityResult extends AsyncResult {

	private final Intent intent;
	private final boolean resultOk;

	public ActivityResult(BoundCallback callback, Intent intent, boolean resultOk) {
		super(callback);
		this.intent = intent;
		this.resultOk = resultOk;
	}

	public boolean isResultOk() {
		return resultOk;
	}

	public <T extends Serializable> T getSingleResult(Class<T> type) {
		return type.cast(getSingleResult());
	}

	public Serializable getSingleResult() {
		return intent == null ? null : intent.getSerializableExtra(SINGLE_RESULT);
	}

	public Intent intent() {
		return intent;
	}

}
