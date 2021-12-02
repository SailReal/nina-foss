package de.ninafoss.presentation.service

import android.app.AlarmManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import de.ninafoss.domain.repository.MessageRepository
import de.ninafoss.presentation.NinaFossApp
import timber.log.Timber

class MessagePollingService : Service() {

	private var runningNotification: AppRunningNotification? = null
	private var worker: Thread? = null
	private var context: Context? = null
	private var messageRepository: MessageRepository? = null

	private fun pollServer() {
		worker = Thread {
			Timber.tag("MessagePollingService").i("Running update messages")
			context?.let { context ->
				messageRepository?.updateMessagesForAllLocations()?.forEach {
					MessageReceivedNotification(context).show(it)
				}
			}
		}
		worker?.start()
	}

	override fun onCreate() {
		super.onCreate()
		Timber.tag("MessagePollingService").d("created")
		runningNotification = AppRunningNotification(this)
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		(application as NinaFossApp).setUpAlarmManager(NinaFossApp.applicationContext().getSystemService(ALARM_SERVICE) as AlarmManager)

		when (intent.getStringExtra(STATUS_ARG)) {
			STATUS.START.name -> {
				Timber.tag("MessagePollingService").i("started")
				runningNotification?.show()
				runningNotification?.notification?.let {
					startForeground(1, it)
				}
			}
			STATUS.UPDATE.name -> {
				Timber.tag("MessagePollingService").i("updated")
				pollServer()
			}
		}

		return START_STICKY
	}

	override fun onDestroy() {
		Timber.tag("MessagePollingService").i("onDestroyed")
		worker?.interrupt()
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

			runningNotification?.notification?.let {
				startForeground(1, it)
			}
		}

	}

	companion object {

		const val STATUS_ARG = "STATUS"

		enum class STATUS {
			START, UPDATE
		}
	}
}
