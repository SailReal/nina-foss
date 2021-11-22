package de.ninafoss.presentation.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import de.ninafoss.domain.Location
import de.ninafoss.util.SharedPreferencesHandler
import timber.log.Timber

class MessagePollingService : Service() {

	private var notification: AppRunningNotification? = null
	private lateinit var context: Context
	private var worker: Thread? = null

	private fun startBackgroundCheck(location: List<Location>) {
		Timber.tag("AutoUploadService").i("Starting background upload")

		val autoUploadFolderPath = SharedPreferencesHandler(context).photoUploadVaultFolder()

		worker = Thread {
			// FIXME
		}
		worker?.start()
	}

	override fun onCreate() {
		super.onCreate()
		Timber.tag("AutoUploadService").d("created")
		notification = AppRunningNotification(this, 0)
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		Timber.tag("AutoUploadService").i("started")
		if (isStartAutoUpload(intent)) {
			Timber.tag("AutoUploadService").i("Received start upload")
			//startBackgroundCheck(locations)
		} else if (isCancelAutoUpload(intent)) {
			Timber.tag("AutoUploadService").i("Received stop auto upload")
			hideNotification()
		} else if (isVaultNotFound(intent)) {
			Timber.tag("AutoUploadService").i("Received show vault not found notification")
			notification!!.showVaultNotFoundNotification()
		}
		return START_STICKY
	}

	private fun isStartAutoUpload(intent: Intent?): Boolean {
		return (intent != null && ACTION_START_AUTO_UPLOAD == intent.action)
	}

	private fun isCancelAutoUpload(intent: Intent?): Boolean {
		return (intent != null && ACTION_CANCEL_AUTO_UPLOAD == intent.action)
	}

	private fun isVaultNotFound(intent: Intent?): Boolean {
		return (intent != null && ACTION_VAULT_NOT_FOUND == intent.action)
	}

	override fun onDestroy() {
		Timber.tag("AutoUploadService").i("onDestroyed")
		worker?.interrupt()
		hideNotification()
	}

	override fun onTaskRemoved(rootIntent: Intent) {
		Timber.tag("AutoUploadService").i("App killed by user")
	}

	override fun onBind(intent: Intent): IBinder {
		return Binder()
	}

	private fun hideNotification() {
		notification?.hide()
	}

	inner class Binder internal constructor() : android.os.Binder() {

		fun init(myContext: Context) {
			context = myContext
		}

	}

	companion object {

		private const val ACTION_START_AUTO_UPLOAD = "START_AUTO_UPLOAD"
		private const val ACTION_CANCEL_AUTO_UPLOAD = "CANCEL_AUTO_UPLOAD"
		private const val ACTION_VAULT_NOT_FOUND = "VAULT_NOT_FOUND"

		private var locations: List<Location>? = null

		fun startService(context: Context, myLocation: List<Location>): Intent {
			locations = myLocation
			val startAutoUpload = Intent(context, MessagePollingService::class.java)
			startAutoUpload.action = ACTION_START_AUTO_UPLOAD
			return startAutoUpload
		}

		fun cancel(context: Context): Intent {
			val cancelAutoUploadIntent = Intent(context, MessagePollingService::class.java)
			cancelAutoUploadIntent.action = ACTION_CANCEL_AUTO_UPLOAD
			return cancelAutoUploadIntent
		}

		fun messageReceived(context: Context): Intent {
			val cancelAutoUploadIntent = Intent(context, MessagePollingService::class.java)
			cancelAutoUploadIntent.action = ACTION_VAULT_NOT_FOUND
			return cancelAutoUploadIntent
		}
	}
}
