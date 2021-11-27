package de.ninafoss.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import de.ninafoss.presentation.ui.activity.SplashActivity

class BootAwareReceiver : BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		when {
			intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true) -> {
				context.startActivity(
					Intent(context, SplashActivity::class.java)
						.addFlags(FLAG_ACTIVITY_NEW_TASK)
				)
			}
		}
	}
}
