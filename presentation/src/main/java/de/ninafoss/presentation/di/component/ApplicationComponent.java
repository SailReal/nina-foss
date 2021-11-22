package de.ninafoss.presentation.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import de.ninafoss.data.repository.RepositoryModule;
import de.ninafoss.data.util.NetworkConnectionCheck;
import de.ninafoss.domain.executor.PostExecutionThread;
import de.ninafoss.domain.executor.ThreadExecutor;
import de.ninafoss.domain.repository.LocationRepository;
import de.ninafoss.domain.repository.MessageRepository;
import de.ninafoss.presentation.di.module.ApplicationModule;
import de.ninafoss.presentation.di.module.ThreadModule;
import de.ninafoss.presentation.util.ContentResolverUtil;
import de.ninafoss.presentation.util.FileUtil;

@Singleton
@Component(modules = {ApplicationModule.class, ThreadModule.class, RepositoryModule.class})
public interface ApplicationComponent {

	Context context();

	ThreadExecutor threadExecutor();

	PostExecutionThread postExecutionThread();

	MessageRepository messageRepository();

	LocationRepository locationRepository();

	//UpdateCheckRepository updateCheckRepository();

	FileUtil fileUtil();

	ContentResolverUtil contentResolverUtil();

	NetworkConnectionCheck networkConnectionCheck();

}
