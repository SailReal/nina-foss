package de.ninafoss.domain.repository;

import java.util.List;
import java.util.Map;

import de.ninafoss.domain.Location;
import de.ninafoss.domain.exception.BackendException;

public interface LocationRepository {

	List<Location> locations() throws BackendException;

	Location store(Location location) throws BackendException;

	void delete(Location location) throws BackendException;

	Map<String, String> allPossibleLocations() throws BackendException;

}
