package de.ninafoss.domain.usecases.location;

import de.ninafoss.domain.Location;
import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.LocationRepository;
import de.ninafoss.generator.Parameter;
import de.ninafoss.generator.UseCase;

@UseCase
class DeleteLocation {

	private final Location location;
	private final LocationRepository locationRepository;

	public DeleteLocation(LocationRepository locationRepository, @Parameter Location location) {
		this.location = location;
		this.locationRepository = locationRepository;
	}

	public void execute() throws BackendException {
		locationRepository.delete(location);
	}
}
