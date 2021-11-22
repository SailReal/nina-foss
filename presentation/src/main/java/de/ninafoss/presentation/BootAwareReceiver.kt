package de.ninafoss.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootAwareReceiver : BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		when {
			intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true) -> {
				TODO("Not yet implemented") // --> start service
			}
		}
	}
}
