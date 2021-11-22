package de.ninafoss.presentation.ui.activity

import android.content.Intent
import android.net.Uri
import javax.inject.Inject
import de.ninafoss.generator.Activity
import de.ninafoss.presentation.R
import de.ninafoss.presentation.model.ProgressModel
import de.ninafoss.presentation.presenter.SettingsPresenter
import de.ninafoss.presentation.ui.activity.view.SettingsView
import de.ninafoss.presentation.ui.dialog.DebugModeDisclaimerDialog
import de.ninafoss.presentation.ui.dialog.UpdateAppAvailableDialog
import de.ninafoss.presentation.ui.dialog.UpdateAppDialog
import de.ninafoss.presentation.ui.fragment.SettingsFragment
import kotlinx.android.synthetic.main.toolbar_layout.toolbar

@Activity(layout = R.layout.activity_settings)
class SettingsActivity : BaseActivity(),
	SettingsView,
	DebugModeDisclaimerDialog.Callback,
	UpdateAppAvailableDialog.Callback, //
	UpdateAppDialog.Callback {

	@Inject
	lateinit var presenter: SettingsPresenter

	override fun setupView() {
		setupToolbar()
	}

	private fun setupToolbar() {
		toolbar.setTitle(R.string.screen_settings_title)
		setSupportActionBar(toolbar)
	}

	fun presenter(): SettingsPresenter = presenter

	override fun onDisclaimerAccepted() {
		presenter.onDebugModeChanged(accepted())
	}

	override fun onDisclaimerRejected() {
		settingsFragment().deactivateDebugMode()
	}

	private fun settingsFragment(): SettingsFragment = supportFragmentManager.findFragmentByTag("SettingsFragment") as SettingsFragment

	private fun accepted(): Boolean = true

	override fun refreshUpdateTimeView() {
		settingsFragment().setupUpdateCheck()
	}

	//override fun snackbarView(): View = settingsFragment().rootView()

	override fun cancelUpdateClicked() {
		// Do nothing
	}

	override fun showUpdateWebsite() {
		val url = "https://cryptomator.org/de/android/"
		val intent = Intent(Intent.ACTION_VIEW)
		intent.data = Uri.parse(url)
		startActivity(intent)
	}

	override fun installUpdate() {
		presenter().installUpdate()
	}

	override fun onUpdateAppDialogLoaded() {
		showProgress(ProgressModel.GENERIC)
	}
}
