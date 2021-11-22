package de.ninafoss.data.db.mappers;

import java.util.ArrayList;
import java.util.List;

import de.ninafoss.data.db.entities.DatabaseEntity;
import de.ninafoss.domain.exception.BackendException;

public abstract class EntityMapper<E extends DatabaseEntity, D> {

	EntityMapper() {
	}

	public List<D> fromEntities(Iterable<E> entities) throws BackendException {
		List<D> result = new ArrayList<>();
		for (E entity : entities) {
			result.add(fromEntity(entity));
		}
		return result;
	}

	public List<E> toEntities(Iterable<D> domainObjects) {
		List<E> result = new ArrayList<>();
		for (D domainObject : domainObjects) {
			result.add(toEntity(domainObject));
		}
		return result;
	}

	protected abstract D fromEntity(E entity) throws BackendException;

	protected abstract E toEntity(D domainObject);

}
