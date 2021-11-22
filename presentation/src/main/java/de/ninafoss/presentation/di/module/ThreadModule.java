package de.ninafoss.presentation.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ninafoss.data.executor.JobExecutor;
import de.ninafoss.domain.executor.PostExecutionThread;
import de.ninafoss.domain.executor.ThreadExecutor;
import de.ninafoss.presentation.UIThread;

@Module
public class ThreadModule {

	@Provides
	@Singleton
	ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
		return jobExecutor;
	}

	@Provides
	@Singleton
	PostExecutionThread providePostExecutionThread(UIThread uiThread) {
		return uiThread;
	}
}
