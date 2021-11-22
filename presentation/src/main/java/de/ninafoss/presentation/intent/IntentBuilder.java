package de.ninafoss.presentation.intent;

import android.content.Intent;

import de.ninafoss.presentation.presenter.ContextHolder;


public interface IntentBuilder {

	void startActivity(ContextHolder contextHolder);

	Intent build(ContextHolder contextHolder);

}
