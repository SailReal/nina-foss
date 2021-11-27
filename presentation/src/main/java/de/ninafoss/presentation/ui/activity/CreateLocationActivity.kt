package de.ninafoss.presentation.ui.activity

import androidx.fragment.app.Fragment
import javax.inject.Inject
import de.ninafoss.domain.Location
import de.ninafoss.generator.Activity
import de.ninafoss.generator.InjectIntent
import de.ninafoss.presentation.R
import de.ninafoss.presentation.intent.CreateLocationIntent
import de.ninafoss.presentation.presenter.CreateLocationPresenter
import de.ninafoss.presentation.ui.activity.view.CreateLocationView
import de.ninafoss.presentation.ui.bottomsheet.SettingsLocationBottomSheet
import de.ninafoss.presentation.ui.fragment.CreateLocationFragment
import kotlinx.android.synthetic.main.toolbar_layout.toolbar

@Activity
class CreateLocationActivity : BaseActivity(), CreateLocationView, SettingsLocationBottomSheet.Callback {

	@Inject
	lateinit var createLocationPresenter: CreateLocationPresenter

	@InjectIntent
	lateinit var createLocationIntent: CreateLocationIntent

	override fun setupView() {
		toolbar.setTitle(R.string.screen_create_location_title)
		setSupportActionBar(toolbar)

		createLocationPresenter.loadPossibleLocations()
	}

	override fun createFragment(): Fragment = CreateLocationFragment.newInstance(createLocationIntent.location())

	override fun onWindowFocusChanged(hasFocus: Boolean) {
		super.onWindowFocusChanged(hasFocus)
		createLocationPresenter.onWindowFocusChanged(hasFocus)
	}

	override fun render(locations: List<Location>) {
		createLocationFragment().showLocations(locations)
	}

	override fun showLocationSettingsDialog(location: Location) {
		val locationSettingDialog = SettingsLocationBottomSheet.newInstance(location)
		locationSettingDialog.show(supportFragmentManager, "VaultSettings")
	}

	override fun setPossibleLocations(locations: Map<String, String>) {
		createLocationFragment().setPossibleLocations(locations)
	}

	override fun onDeleteLocationClicked(location: Location) {
		createLocationPresenter.deleteLocation(location)
	}

	private fun createLocationFragment(): CreateLocationFragment = getCurrentFragment(R.id.fragmentContainer) as CreateLocationFragment

}
