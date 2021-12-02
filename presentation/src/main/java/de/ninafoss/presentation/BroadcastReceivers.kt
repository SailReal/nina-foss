package de.ninafoss.presentation

import android.app.AlarmManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import de.ninafoss.presentation.ui.activity.SplashActivity

class BroadcastReceivers : BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		when (intent.action) {
			Intent.ACTION_BOOT_COMPLETED -> {
				context.startActivity(
					Intent(context, SplashActivity::class.java)
						.addFlags(FLAG_ACTIVITY_NEW_TASK)
				)
			}
			AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> {
				if (context is NinaFossApp) {
					context.setUpAlarmManager(NinaFossApp.applicationContext().getSystemService(Service.ALARM_SERVICE) as AlarmManager)
				} else {
					// TODO
				}
			}
		}
	}
}
