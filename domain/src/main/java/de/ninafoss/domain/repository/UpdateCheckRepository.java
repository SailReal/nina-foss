package de.ninafoss.domain.repository;

import com.google.common.base.Optional;

import java.io.File;

import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.exception.GeneralUpdateErrorException;
import de.ninafoss.domain.usecases.UpdateCheck;

public interface UpdateCheckRepository {

	Optional<UpdateCheck> getUpdateCheck(String version) throws BackendException;

	void update(File file) throws GeneralUpdateErrorException;

}
