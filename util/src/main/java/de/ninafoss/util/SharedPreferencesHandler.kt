package de.ninafoss.util

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import com.google.common.base.Optional
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs

class SharedPreferencesHandler @Inject
constructor(context: Context) {

	private val defaultSharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

	val pollingInterval: PollingInterval
		get() {
			val value = defaultSharedPreferences.getValue(POLLING_INTERVAL, PollingInterval.FIFTEEN_MINUTES.name)
			return PollingInterval.valueOf(value)
		}

	val screenStyleMode: Int
		get() {
			return when (defaultSharedPreferences.getValue(SCREEN_STYLE_MODE, "MODE_NIGHT_FOLLOW_SYSTEM")) {
				"MODE_NIGHT_FOLLOW_SYSTEM" -> MODE_NIGHT_FOLLOW_SYSTEM
				"MODE_NIGHT_NO" -> MODE_NIGHT_NO
				"MODE_NIGHT_YES" -> MODE_NIGHT_YES
				else -> MODE_NIGHT_FOLLOW_SYSTEM
			}
		}

	fun setScreenStyleMode(newValue: String) {
		defaultSharedPreferences.setValue(SCREEN_STYLE_MODE, newValue)
	}

	fun debugMode(): Boolean {
		return defaultSharedPreferences.getValue(DEBUG_MODE, false)
	}

	fun setDebugMode(enabled: Boolean) {
		defaultSharedPreferences //
			.setValue(DEBUG_MODE, enabled)
	}

	private fun updateIntervalInDays(): Optional<Int> {
		val updateInterval = defaultSharedPreferences.getValue(UPDATE_INTERVAL, "7")

		if (updateInterval == "Never") {
			return Optional.absent()
		}

		return Optional.of(Integer.parseInt(updateInterval))
	}

	fun lastUpdateCheck(): Date? {
		val date = defaultSharedPreferences.getString(LAST_UPDATE_CHECK, "")
		if (date.isNullOrEmpty()) {
			return null
		}
		return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(date)
	}

	fun updateExecuted() {
		val formatted = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Date())
		defaultSharedPreferences.setValue(LAST_UPDATE_CHECK, formatted)
	}

	fun doUpdate(): Boolean {
		val updateIntervalInDays = updateIntervalInDays()
		if (!updateIntervalInDays.isPresent) {
			return false
		}

		val lastUpdateCheck = lastUpdateCheck() ?: return true

		val currentDate = Date()
		val different = abs(currentDate.time - lastUpdateCheck.time)

		val secondsInMilli = 1000
		val minutesInMilli = secondsInMilli * 60
		val hoursInMilli = minutesInMilli * 60
		val daysInMilli = hoursInMilli * 24

		return different / daysInMilli >= updateIntervalInDays.get()
	}

	companion object {

		const val DEBUG_MODE = "debugMode"
		const val SECURE_SCREEN = "secureScreen"
		const val SCREEN_STYLE_MODE = "screenStyleMode"
		const val UPDATE_INTERVAL = "updateInterval"
		const val POLLING_INTERVAL = "pollingInterval"
		private const val LAST_UPDATE_CHECK = "lastUpdateCheck"
	}

	private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
		val editor = this.edit()
		operation(editor)
		editor.apply()
	}

	private fun SharedPreferences.clear() {
		val editor = this.edit()
		editor.clear()
		editor.apply()
	}

	private fun SharedPreferences.setValue(key: String, value: Any?) {
		set(key, value)
	}

	private operator fun SharedPreferences.set(key: String, value: Any?) {
		when (value) {
			is String? -> edit { it.putString(key, value) }
			is Int -> edit { it.putInt(key, value) }
			is Boolean -> edit { it.putBoolean(key, value) }
			is Float -> edit { it.putFloat(key, value) }
			is Long -> edit { it.putLong(key, value) }
			else -> throw UnsupportedOperationException("Not yet implemented")
		}
	}

	/**
	 * finds value on given key.
	 * [T] is the type of value
	 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
	 */
	private inline fun <reified T : Any> SharedPreferences.getValue(key: String, defaultValue: T? = null): T {
		return get(key, defaultValue)
	}

	/**
	 * finds value on given key.
	 * [T] is the type of value
	 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
	 */
	private inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T {
		return when (T::class) {
			String::class -> getString(key, defaultValue as? String) as T
			Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
			Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
			Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
			Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
			else -> throw UnsupportedOperationException("Not yet implemented")
		}
	}
}
