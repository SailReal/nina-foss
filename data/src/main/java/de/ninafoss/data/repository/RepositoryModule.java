package de.ninafoss.data.repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ninafoss.domain.repository.LocationRepository;
import de.ninafoss.domain.repository.MessageRepository;

@Module
public class RepositoryModule {

	@Singleton
	@Provides
	public LocationRepository provideLocationRepository(LocationRepositoryImpl locationRepository) {
		return locationRepository;
	}

	@Singleton
	@Provides
	public MessageRepository provideMessageRepository(MessageRepositoryImpl messageRepository) {
		return messageRepository;
	}

}
