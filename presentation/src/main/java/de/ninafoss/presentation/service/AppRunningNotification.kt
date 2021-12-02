package de.ninafoss.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import androidx.core.app.NotificationCompat
import de.ninafoss.presentation.R
import de.ninafoss.presentation.util.ResourceHelper.Companion.getColor

class AppRunningNotification(context: Context) {

	lateinit var notification: Notification

	private val builder: NotificationCompat.Builder
	private var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

	init {

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			val notificationChannel = NotificationChannel( //
				NOTIFICATION_CHANNEL_ID, //
				NOTIFICATION_CHANNEL_NAME, //
				IMPORTANCE_HIGH
			)
			notificationManager.createNotificationChannel(notificationChannel)
		}

		this.builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID) //
			.setContentTitle(context.getString(R.string.notification_app_running_title)) //
			.setSmallIcon(R.drawable.background_splash_nina) //
			.setColor(getColor(R.color.colorPrimary)) //
			.setGroup(NOTIFICATION_GROUP_KEY)
			.setAutoCancel(false)
			.setOngoing(true)
	}

	/*private fun showErrorWithMessage(message: String) {
		builder.setContentIntent(startTheActivity())
		builder //
			.setContentTitle(context.getString(R.string.notification_auto_upload_failed_title))
			.setContentText(message) //
			.setProgress(0, 0, false)
			.setAutoCancel(true)
			.setOngoing(false)
			.mActions.clear()
		show()
	}*/

	fun show() {
		this.notification = builder.build()
	}

	fun hide() {
		notificationManager.cancel(NOTIFICATION_ID)
	}

	companion object {

		private const val NOTIFICATION_ID = 94874
		private const val NOTIFICATION_CHANNEL_ID = "65478"
		private const val NOTIFICATION_CHANNEL_NAME = "NINA"
		private const val NOTIFICATION_GROUP_KEY = "NINAGroup"
	}
}
