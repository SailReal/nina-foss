package de.ninafoss.presentation.ui.activity

import androidx.fragment.app.Fragment
import javax.inject.Inject
import de.ninafoss.generator.Activity
import de.ninafoss.generator.InjectIntent
import de.ninafoss.presentation.R
import de.ninafoss.presentation.intent.CreateLocationIntent
import de.ninafoss.presentation.presenter.CreateLocationPresenter
import de.ninafoss.presentation.ui.activity.view.CreateLocationView
import de.ninafoss.presentation.ui.fragment.CreateLocationFragment
import kotlinx.android.synthetic.main.toolbar_layout.toolbar

@Activity
class CreateLocationActivity : BaseActivity(), CreateLocationView {

	@Inject
	lateinit var createLocationPresenter: CreateLocationPresenter

	@InjectIntent
	lateinit var createLocationIntent: CreateLocationIntent

	override fun setupView() {
		toolbar.setTitle(R.string.screen_create_location_title)
		setSupportActionBar(toolbar)
	}

	override fun createFragment(): Fragment = CreateLocationFragment.newInstance(createLocationIntent.location())

	private fun createLocationFragment(): CreateLocationFragment = getCurrentFragment(R.id.fragmentContainer) as CreateLocationFragment

}
