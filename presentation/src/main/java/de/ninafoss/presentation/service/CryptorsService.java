package de.ninafoss.presentation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import de.ninafoss.util.SharedPreferencesHandler;
import timber.log.Timber;

public class CryptorsService extends Service {

	private final Thread worker = new Thread(() -> {});
	private SharedPreferencesHandler sharedPreferencesHandler;

	@Override
	public void onCreate() {
		super.onCreate();
		Timber.tag("CryptorsService").d("created");
		AppRunningNotification notification = new AppRunningNotification(this, 0);
		sharedPreferencesHandler = new SharedPreferencesHandler(this);
		worker.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Timber.tag("CryptorsService").i("started");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		worker.interrupt();
		Timber.tag("CryptorsService").i("destroyed");
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		Timber.tag("CryptorsService").i("App killed by user");
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return new Binder();
	}

	private void stopCryptorsService() {
		Intent myService = new Intent(CryptorsService.this, CryptorsService.class);
		stopService(myService);
	}

	public class Binder extends android.os.Binder {

		Binder() {
			//cryptors.setOnChangeListener(() -> onUnlockCountChanged(cryptors.size()));
		}
	}
}
