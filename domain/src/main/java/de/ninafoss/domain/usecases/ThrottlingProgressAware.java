package de.ninafoss.domain.usecases;

import de.ninafoss.domain.usecases.location.Progress;
import de.ninafoss.domain.usecases.location.ProgressState;

import timber.log.Timber;

import static java.lang.System.currentTimeMillis;

public class ThrottlingProgressAware<T extends ProgressState> implements ProgressAware<T> {

	private static final long THROTTLE_INTERVAL_MS = 40L;

	private final ProgressAware<T> progressAware;

	private Progress lastProgress;
	private long nextProgressDelegation = 0L;

	public ThrottlingProgressAware(ProgressAware<T> progressAware) {
		this.progressAware = progressAware;
	}

	@Override
	public void onProgress(Progress<T> progress) {
		if (progress.stateOrCompletionChanged(lastProgress) || //
				progress.percentageChanged(lastProgress) && throttleIntervalHasPassed()) {
			Timber.tag("Progress").v(progress.toString());
			nextProgressDelegation = currentTimeMillis() + THROTTLE_INTERVAL_MS;
			lastProgress = progress;
			progressAware.onProgress(progress);
		}
	}

	private boolean throttleIntervalHasPassed() {
		return nextProgressDelegation < currentTimeMillis();
	}
}
