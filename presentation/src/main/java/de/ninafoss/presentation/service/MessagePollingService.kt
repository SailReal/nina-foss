package de.ninafoss.presentation.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import java.util.Timer
import java.util.TimerTask
import de.ninafoss.domain.repository.MessageRepository
import de.ninafoss.util.SharedPreferencesHandler
import timber.log.Timber

class MessagePollingService : Service() {

	private val timer = Timer()

	private var runningNotification: AppRunningNotification? = null
	private var timerTask: TimerTask? = null

	private lateinit var context: Context
	private lateinit var messageRepository: MessageRepository

	private fun startPolling() {
		Timber.tag("MessagePollingService").i("Starting background polling")

		timerTask = object : TimerTask() {
			override fun run() {
				Timber.tag("MessagePollingService").i("Running update messages")
				messageRepository.updateMessagesForAllLocations().forEach {
					MessageReceivedNotification(context).show(it)
				}
			}
		}

		timer.schedule(timerTask, 0, SharedPreferencesHandler(context).pollingInterval.durationMillis)
	}

	override fun onCreate() {
		super.onCreate()
		Timber.tag("MessagePollingService").d("created")
		runningNotification = AppRunningNotification(this)
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		Timber.tag("MessagePollingService").i("started")
		return START_STICKY
	}

	override fun onDestroy() {
		Timber.tag("MessagePollingService").i("onDestroyed")
		timerTask?.cancel()
		timer.cancel()
		hideNotification()
	}

	override fun onTaskRemoved(rootIntent: Intent) {
		Timber.tag("MessagePollingService").i("App killed by user")
	}

	override fun onBind(intent: Intent): IBinder {
		return Binder()
	}

	private fun hideNotification() {
		runningNotification?.hide()
	}

	inner class Binder internal constructor() : android.os.Binder() {

		fun init(myContext: Context, myMessageRepository: MessageRepository) {
			context = myContext
			messageRepository = myMessageRepository
			runningNotification?.show()
			startPolling()
		}

	}

	companion object {
	}
}
