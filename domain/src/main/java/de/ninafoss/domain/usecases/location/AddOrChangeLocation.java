package de.ninafoss.domain.usecases.location;

import de.ninafoss.domain.Location;
import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.LocationRepository;
import de.ninafoss.generator.Parameter;
import de.ninafoss.generator.UseCase;

@UseCase
public class AddOrChangeLocation {

	private final Location location;
	private final LocationRepository locationRepository;

	public AddOrChangeLocation(LocationRepository locationRepository, //
			@Parameter Location location) {
		this.location = location;
		this.locationRepository = locationRepository;
	}

	public void execute() throws BackendException {
		if (cloudExists(location)) {
			//throw new CloudAlreadyExistsException(); TODO
		}
		locationRepository.store(location);
	}

	private boolean cloudExists(Location cloud) throws BackendException {
		for (Location storedCloud : locationRepository.locations()) {
			if (cloud.id() != null && cloud.id().equals(storedCloud.id())) {
				continue;
			}
			return true;
		}
		return false;
	}

}
