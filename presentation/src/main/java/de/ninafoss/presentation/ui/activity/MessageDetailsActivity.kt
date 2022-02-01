package de.ninafoss.presentation.ui.activity

import androidx.fragment.app.Fragment
import de.ninafoss.generator.Activity
import de.ninafoss.generator.InjectIntent
import de.ninafoss.presentation.R
import de.ninafoss.presentation.intent.MessageDetailsIntent
import de.ninafoss.presentation.presenter.MessageDetailsPresenter
import de.ninafoss.presentation.ui.activity.view.MessageDetailsView
import de.ninafoss.presentation.ui.fragment.MessageDetailsFragment
import kotlinx.android.synthetic.main.toolbar_layout.*
import javax.inject.Inject

@Activity(layout = R.layout.activity_layout)
class MessageDetailsActivity : BaseActivity(), MessageDetailsView {

	@Inject
	lateinit var messageDetailsPresenter: MessageDetailsPresenter

	@InjectIntent
	lateinit var messageDetailsIntent: MessageDetailsIntent

	override fun setupView() {
		setupToolbar()
	}

	override fun createFragment(): Fragment = MessageDetailsFragment.newInstance(messageDetailsIntent.message())

	//override fun snackbarView(): View = messageListFragment().rootView()

	override fun getCustomMenuResource(): Int = R.menu.menu_vault_list

	override fun onMenuItemSelected(itemId: Int): Boolean = when (itemId) {
		R.id.action_settings -> {
			//messageListPresenter.startIntent(settingsIntent())
			true
		}
		else -> super.onMenuItemSelected(itemId)
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.app_name).uppercase()
		setSupportActionBar(toolbar)
	}

}
