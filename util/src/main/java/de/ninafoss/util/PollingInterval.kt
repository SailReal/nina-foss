package de.ninafoss.util

import java.util.concurrent.TimeUnit

enum class PollingInterval(private val duration: Int, private var unit: TimeUnit) {
	FIFTEEN_MINUTES(15, TimeUnit.MINUTES),
	ONE_HOUR(1, TimeUnit.HOURS),
	SIX_HOUR(6, TimeUnit.HOURS);

	val durationMillis: Long
		get() {
			return unit.toMillis(duration.toLong())
		}
}
