package de.ninafoss.domain.usecases.location;

import java.util.Map;

import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.LocationRepository;
import de.ninafoss.generator.UseCase;

@UseCase
public class ListAllPossibleLocation {

	private final LocationRepository locationRepository;

	ListAllPossibleLocation(final LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	public Map<String, String> execute() throws BackendException {
		return locationRepository.allPossibleLocations();
	}
}
