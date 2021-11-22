package de.ninafoss.domain.usecases;

import de.ninafoss.domain.usecases.location.Progress;
import de.ninafoss.domain.usecases.location.ProgressState;

public interface ProgressAware<T extends ProgressState> {

	ProgressAware NO_OP_PROGRESS_AWARE_DOWNLOAD = progress -> {
	};

	ProgressAware NO_OP_PROGRESS_AWARE_UPLOAD = progress -> {
	};

	void onProgress(Progress<T> progress);

}
