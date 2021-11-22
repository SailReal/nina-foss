package de.ninafoss.presentation.di.module;

import android.app.Activity;

import de.ninafoss.domain.di.PerView;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

	private final Activity activity;

	public ActivityModule(Activity activity) {
		this.activity = activity;
	}

	@Provides
	@PerView
	Activity activity() {
		return this.activity;
	}
}
