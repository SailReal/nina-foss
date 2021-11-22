package de.ninafoss.domain.repository;

import java.util.List;

import de.ninafoss.domain.Location;
import de.ninafoss.domain.exception.BackendException;

public interface LocationRepository {

	List<Location> locations() throws BackendException;

	Location store(Location location) throws BackendException;

	void delete(Location location) throws BackendException;

	void create(String code) throws BackendException;

}
