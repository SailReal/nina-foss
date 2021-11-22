package de.ninafoss.presentation.ui.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import java.lang.Boolean.TRUE
import java.lang.String.format
import de.ninafoss.presentation.BuildConfig
import de.ninafoss.presentation.R
import de.ninafoss.presentation.ui.activity.SettingsActivity
import de.ninafoss.presentation.ui.dialog.DebugModeDisclaimerDialog
import de.ninafoss.util.SharedPreferencesHandler

class SettingsFragment : PreferenceFragmentCompat() {

	private lateinit var sharedPreferencesHandler: SharedPreferencesHandler

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		sharedPreferencesHandler = SharedPreferencesHandler(activity())
		addPreferencesFromResource(R.xml.preferences)
		setupAppVersion()
	}

	private val sendErrorReportClickListener = Preference.OnPreferenceClickListener {
		onSendErrorReportClicked()
		true
	}

	private val debugModeChangedListener = Preference.OnPreferenceChangeListener { _, newValue ->
		onDebugModeChanged(TRUE == newValue)
		true
	}

	private val updateCheckClickListener = Preference.OnPreferenceClickListener {
		onCheckUpdateClicked()
		true
	}

	private val screenStyleModeChangedListener = Preference.OnPreferenceChangeListener { _, newValue ->
		sharedPreferencesHandler.setScreenStyleMode(newValue as String)
		AppCompatDelegate.setDefaultNightMode(sharedPreferencesHandler.screenStyleMode)
		activity().delegate.localNightMode = sharedPreferencesHandler.screenStyleMode
		true
	}

	private fun activity(): SettingsActivity = this.activity as SettingsActivity

	private fun setupAppVersion() {
		val preference = findPreference(APP_VERSION_ITEM_KEY) as Preference?
		val versionName = SpannableString(BuildConfig.VERSION_NAME)
		versionName.setSpan( //
			ForegroundColorSpan(ContextCompat.getColor(activity(), R.color.textColorLight)), //
			0, versionName.length, 0
		)
		preference?.summary = versionName
	}

	private fun removeUpdateCheck() {
		val versionCategory = findPreference("versionCategory") as PreferenceCategory?
		versionCategory?.removePreference(findPreference(UPDATE_CHECK_ITEM_KEY))
		versionCategory?.removePreference(findPreference(UPDATE_INTERVAL_ITEM_KEY))
	}

	fun setupUpdateCheck() {
		val preference = findPreference(UPDATE_CHECK_ITEM_KEY) as Preference?

		val lastUpdateCheck = sharedPreferencesHandler.lastUpdateCheck()
		val readableDate: String = if (lastUpdateCheck != null) {
			val dateFormatUser = android.text.format.DateFormat.getLongDateFormat(context)
			val strDate: String = dateFormatUser.format(lastUpdateCheck)
			format(getString(R.string.screen_settings_last_check_updates), strDate)
		} else {
			getString(R.string.screen_settings_last_check_updates_never)
		}

		val date = SpannableString(readableDate)

		date.setSpan( //
			ForegroundColorSpan(ContextCompat.getColor(activity(), R.color.textColorLight)), //
			0, date.length, 0
		)
		preference?.summary = date
	}

	override fun onResume() {
		super.onResume()
		(findPreference(SEND_ERROR_REPORT_ITEM_KEY) as Preference?)?.onPreferenceClickListener = sendErrorReportClickListener
		(findPreference(SharedPreferencesHandler.DEBUG_MODE) as Preference?)?.onPreferenceChangeListener = debugModeChangedListener
		(findPreference(SharedPreferencesHandler.SCREEN_STYLE_MODE) as Preference?)?.onPreferenceChangeListener = screenStyleModeChangedListener
		if (BuildConfig.FLAVOR == "apkstore") {
			(findPreference(UPDATE_CHECK_ITEM_KEY) as Preference?)?.onPreferenceClickListener = updateCheckClickListener
		}
	}

	fun deactivateDebugMode() {
		sharedPreferencesHandler.setDebugMode(false)
		(findPreference(SharedPreferencesHandler.DEBUG_MODE) as SwitchPreferenceCompat?)?.isChecked = false
	}

	fun secureScreen() {
		sharedPreferencesHandler.setSecureScreen(true)
		(findPreference(SharedPreferencesHandler.SECURE_SCREEN) as SwitchPreferenceCompat?)?.isChecked = true
	}

	private fun onSendErrorReportClicked() {
		activity().presenter().onSendErrorReportClicked()
	}

	private fun onCheckUpdateClicked() {
		activity().presenter().onCheckUpdateClicked()
	}

	private fun onDebugModeChanged(enabled: Boolean) {
		if (enabled) {
			activity().showDialog(DebugModeDisclaimerDialog.newInstance())
		} else {
			activity().presenter().onDebugModeChanged(false)
		}
	}

	fun rootView(): View {
		return activity().findViewById(R.id.activityRootView)
	}

	companion object {

		private const val APP_VERSION_ITEM_KEY = "appVersion"
		private const val SEND_ERROR_REPORT_ITEM_KEY = "sendErrorReport"
		private const val UPDATE_CHECK_ITEM_KEY = "updateCheck"
		private const val UPDATE_INTERVAL_ITEM_KEY = "updateInterval"
	}

}
