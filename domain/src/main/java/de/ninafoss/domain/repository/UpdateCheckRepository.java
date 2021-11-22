package de.ninafoss.domain.repository;

import com.google.common.base.Optional;

import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.usecases.UpdateCheck;

public interface UpdateCheckRepository {

	Optional<UpdateCheck> getUpdateCheck(String version) throws BackendException;

	String getLicense();

	void setLicense(String license);

	//void update(File file) throws GeneralUpdateErrorException;
}
