package de.ninafoss.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Context
import androidx.core.app.NotificationCompat
import de.ninafoss.domain.Message
import de.ninafoss.presentation.R
import de.ninafoss.presentation.util.ResourceHelper.Companion.getColor
import kotlin.random.Random

class MessageReceivedNotification(private val context: Context) {

	private val builder: NotificationCompat.Builder
	private var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
	private val notificationId = Random.nextInt()

	init {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			val notificationChannel = NotificationChannel( //
				NOTIFICATION_CHANNEL_ID, //
				NOTIFICATION_CHANNEL_NAME, //
				IMPORTANCE_LOW
			)
			notificationManager.createNotificationChannel(notificationChannel)
		}

		this.builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID) //
			.setContentTitle(context.getString(R.string.notification_message_received_title)) //
			.setSmallIcon(R.drawable.background_splash_cryptomator) //
			.setColor(getColor(R.color.colorPrimary)) //
			.setGroup(NOTIFICATION_GROUP_KEY)
			.setOngoing(false)
	}

	fun show(message: Message) {
		builder.setContentText(message.headline)
		show()
	}

	fun show() {
		notificationManager.notify(notificationId, builder.build())
	}

	fun hide() {
		notificationManager.cancel(notificationId)
	}

	companion object {

		private const val NOTIFICATION_CHANNEL_ID = "65478"
		private const val NOTIFICATION_CHANNEL_NAME = "NINA"
		private const val NOTIFICATION_GROUP_KEY = "NINAGroup"
	}
}
