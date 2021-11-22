package de.ninafoss.domain.usecases;

public abstract class NoOpResultHandler<T> implements ResultHandler<T> {

	@Override
	public void onFinished() {
		// no-op
	}

	@Override
	public void onError(Throwable e) {
		// no-op
	}

	@Override
	public void onSuccess(T t) {
		// no-op
	}
}
