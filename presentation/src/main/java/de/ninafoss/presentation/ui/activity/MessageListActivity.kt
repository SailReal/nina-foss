package de.ninafoss.presentation.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import javax.inject.Inject
import de.ninafoss.domain.Message
import de.ninafoss.generator.Activity
import de.ninafoss.presentation.R
import de.ninafoss.presentation.intent.Intents.createLocationIntent
import de.ninafoss.presentation.intent.Intents.settingsIntent
import de.ninafoss.presentation.model.ProgressModel
import de.ninafoss.presentation.presenter.MessageListPresenter
import de.ninafoss.presentation.ui.activity.view.MessageListView
import de.ninafoss.presentation.ui.dialog.UpdateAppAvailableDialog
import de.ninafoss.presentation.ui.dialog.UpdateAppDialog
import de.ninafoss.presentation.ui.fragment.MessageListFragment
import kotlinx.android.synthetic.main.toolbar_layout.toolbar

@Activity(layout = R.layout.activity_layout)
class MessageListActivity : BaseActivity(), //
	MessageListView, //
	//SettingsMessageBottomSheet.Callback, //
	UpdateAppAvailableDialog.Callback, //
	UpdateAppDialog.Callback {

	@Inject
	lateinit var messageListPresenter: MessageListPresenter

	/*@InjectIntent
	lateinit var messageListIntent: MessageListIntent*/

	override fun onWindowFocusChanged(hasFocus: Boolean) {
		super.onWindowFocusChanged(hasFocus)
		messageListPresenter.onWindowFocusChanged(hasFocus)
	}

	override fun setupView() {
		setupToolbar()
		messageListPresenter.prepareView()
	}

	override fun createFragment(): Fragment = MessageListFragment()

	//override fun snackbarView(): View = messageListFragment().rootView()

	override fun getCustomMenuResource(): Int = R.menu.menu_vault_list

	override fun onMenuItemSelected(itemId: Int): Boolean = when (itemId) {
		R.id.action_settings -> {
			messageListPresenter.startIntent(settingsIntent())
			true
		}
		else -> super.onMenuItemSelected(itemId)
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.app_name).uppercase()
		setSupportActionBar(toolbar)
	}

	/*override fun showMessageSettingsDialog(message: Message) {
		val vaultSettingDialog = //
			SettingsVaultBottomSheet.newInstance(message)
		vaultSettingDialog.show(supportFragmentManager, "VaultSettings")
	}

	override fun renderMessageList(message: List<Message>) {
		messageListFragment().showVaults(message)
	}

	override fun deleteMessageFromAdapter(messageId: Long) {
		messageListFragment().deleteVaultFromAdapter(messageId)
	}

	override fun addOrUpdateMessage(message: Message) {
		messageListFragment().addOrUpdateVault(message)
	}

	override fun onDeleteVaultClick(vaultModel: VaultModel) {
		VaultDeleteConfirmationDialog.newInstance(vaultModel) //
			.show(supportFragmentManager, "VaultDeleteConfirmationDialog")
	}

	override fun onDeleteConfirmedClick(vaultModel: VaultModel) {
		messageListPresenter.deleteVault(vaultModel)
	}*/

	private fun messageListFragment(): MessageListFragment = //
		getCurrentFragment(R.id.fragmentContainer) as MessageListFragment

	override fun onUpdateAppDialogLoaded() {
		showProgress(ProgressModel.GENERIC)
	}

	override fun installUpdate() {
		messageListPresenter.installUpdate()
	}

	override fun cancelUpdateClicked() {
		closeDialog()
	}

	override fun showUpdateWebsite() {
		val url = "https://cryptomator.org/de/android/" // FIXME
		val intent = Intent(Intent.ACTION_VIEW)
		intent.data = Uri.parse(url)
		startActivity(intent)
	}

	override fun showEmptyMessagesHint() {
		TODO("Not yet implemented")
	}

	override fun hideEmptyMessagesHint() {
	}

	override fun renderMessages(messages: List<Message>) {
		messageListFragment().showMessages(messages)
	}

	override fun showMessagetSettingsDialog(message: Message) {
		TODO("Not yet implemented")
	}

	override fun showCreateLocationView() {
		messageListPresenter.startIntent(createLocationIntent())
	}
}
