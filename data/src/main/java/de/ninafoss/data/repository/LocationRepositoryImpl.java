package de.ninafoss.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ninafoss.data.db.Database;
import de.ninafoss.data.db.entities.LocationEntity;
import de.ninafoss.data.db.mappers.LocationEntityMapper;
import de.ninafoss.domain.Location;
import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.LocationRepository;

@Singleton
class LocationRepositoryImpl implements LocationRepository {

	private final Database database;
	private final LocationEntityMapper mapper;

	@Inject
	public LocationRepositoryImpl(LocationEntityMapper mapper, Database database) {
		this.database = database;
		this.mapper = mapper;
	}

	@Override
	public List<Location> locations() throws BackendException {
		return mapper.fromEntities(database.loadAll(LocationEntity.class));
	}

	@Override
	public Location store(Location location) {
		Location storedLocation = mapper.fromEntity(database.store(mapper.toEntity(location)));
		database.clearCache();
		return storedLocation;
	}

	@Override
	public void delete(Location location) {
		database.delete(mapper.toEntity(location));
	}

	@Override
	public void create(String code) throws BackendException {
		// FIXME
	}
}
