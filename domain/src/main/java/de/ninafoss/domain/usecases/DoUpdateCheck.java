package de.ninafoss.domain.usecases;

import de.ninafoss.domain.exception.BackendException;
import de.ninafoss.domain.repository.UpdateCheckRepository;
import de.ninafoss.generator.Parameter;
import de.ninafoss.generator.UseCase;
import com.google.common.base.Optional;

@UseCase
public class DoUpdateCheck {

	private final String version;
	private final UpdateCheckRepository updateCheckRepository;

	DoUpdateCheck(final UpdateCheckRepository updateCheckRepository, @Parameter String version) {
		this.updateCheckRepository = updateCheckRepository;
		this.version = version;
	}

	public Optional<UpdateCheck> execute() throws BackendException {
		return updateCheckRepository.getUpdateCheck(version);
	}
}
