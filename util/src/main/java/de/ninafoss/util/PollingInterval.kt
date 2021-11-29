package de.ninafoss.util

import java.util.concurrent.TimeUnit

enum class PollingInterval(private val duration: Int, private var unit: TimeUnit) {
	FIVE_MINUTES(5, TimeUnit.MINUTES),
	FIFTEEN_MINUTES(15, TimeUnit.MINUTES),
	ONE_HOUR(1, TimeUnit.HOURS),
	SIX_HOUR(6, TimeUnit.HOURS);

	val durationMillis: Long
		get() {
			return unit.toMillis(duration.toLong())
		}
}
