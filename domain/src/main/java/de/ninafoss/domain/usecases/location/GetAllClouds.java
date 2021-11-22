package de.ninafoss.domain.usecases.location;

import java.util.List;

import de.ninafoss.domain.Location;
import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.LocationRepository;
import de.ninafoss.generator.UseCase;

@UseCase
class GetAllClouds {

	private final LocationRepository locationRepository;

	public GetAllClouds(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	public List<Location> execute() throws BackendException {
		return locationRepository.locations();
	}
}
