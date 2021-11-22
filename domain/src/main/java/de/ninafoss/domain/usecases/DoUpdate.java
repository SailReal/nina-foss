package de.ninafoss.domain.usecases;

import java.io.File;

import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.UpdateCheckRepository;
import de.ninafoss.generator.Parameter;
import de.ninafoss.generator.UseCase;

@UseCase
public class DoUpdate {

	private final UpdateCheckRepository updateCheckRepository;
	private final File file;

	DoUpdate(final UpdateCheckRepository updateCheckRepository, @Parameter File file) {
		this.updateCheckRepository = updateCheckRepository;
		this.file = file;
	}

	public void execute() throws BackendException {
		//updateCheckRepository.update(file); FIXME
	}
}
