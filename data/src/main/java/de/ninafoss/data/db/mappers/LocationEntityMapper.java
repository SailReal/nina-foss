package de.ninafoss.data.db.mappers;

import static de.ninafoss.domain.Location.aLocation;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ninafoss.data.db.entities.LocationEntity;
import de.ninafoss.domain.Location;

@Singleton
public class LocationEntityMapper extends EntityMapper<LocationEntity, Location> {

	@Inject
	public LocationEntityMapper() {
	}

	@Override
	public Location fromEntity(LocationEntity entity) {
		return aLocation() //
				.withId(entity.getId()) //
				.withCode(entity.getCode()) //
				.withName(entity.getName()) //
				.build();
	}

	@Override
	public LocationEntity toEntity(Location domainObject) {
		LocationEntity result = new LocationEntity();
		result.setId(domainObject.id());
		result.setCode(domainObject.code());
		result.setName(domainObject.name());
		return result;
	}

}
