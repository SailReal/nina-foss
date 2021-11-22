package de.ninafoss.presentation.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ninafoss.presentation.NinaFossApp;

@Module
public class ApplicationModule {

	private final NinaFossApp application;

	public ApplicationModule(NinaFossApp application) {
		this.application = application;
	}

	@Provides
	@Singleton
	Context provideApplicationContext() {
		return application;
	}
}
