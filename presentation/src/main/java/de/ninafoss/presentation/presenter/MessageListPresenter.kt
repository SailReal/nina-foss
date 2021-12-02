package de.ninafoss.presentation.presenter

import android.content.Context
import javax.inject.Inject
import de.ninafoss.data.util.NetworkConnectionCheck
import de.ninafoss.domain.Message
import de.ninafoss.domain.di.PerView
import de.ninafoss.domain.usecases.UpdateCheck
import de.ninafoss.domain.usecases.message.GetMessageListUseCase
import de.ninafoss.domain.usecases.message.UpdateMessagesUseCase
import de.ninafoss.presentation.exception.ExceptionHandlers
import de.ninafoss.presentation.ui.activity.view.MessageListView
import de.ninafoss.presentation.ui.dialog.DisclaimerDialog
import de.ninafoss.presentation.util.FileUtil
import de.ninafoss.util.SharedPreferencesHandler
import timber.log.Timber

@PerView
class MessageListPresenter @Inject constructor( //
	private val getMessageListUseCase: GetMessageListUseCase,
	private val updateMessagesUseCase: UpdateMessagesUseCase,
	//private val deleteVaultUseCase: DeleteVaultUseCase,
	/*private val updateCheckUseCase: DoUpdateCheckUseCase,  //
	private val updateUseCase: DoUpdateUseCase,  *///
	private val networkConnectionCheck: NetworkConnectionCheck,  //
	private val fileUtil: FileUtil,  //
	private val sharedPreferencesHandler: SharedPreferencesHandler,  //
	exceptionMappings: ExceptionHandlers
) : Presenter<MessageListView>(exceptionMappings) {

	fun onWindowFocusChanged(hasFocus: Boolean) {
		if (hasFocus) {
			loadMessageList()
		}
	}

	fun prepareView() {
		if(!sharedPreferencesHandler.disclaimerAccepted()) {
			view?.showDialog(DisclaimerDialog.newInstance())
		}

		checkForAppUpdates()
	}

	private fun checkForAppUpdates() {
		if (networkConnectionCheck.isPresent) {
			/*updateCheckUseCase //
				.withVersion(BuildConfig.VERSION_NAME) //
				.run(object : NoOpResultHandler<Optional<UpdateCheck>>() {
					override fun onSuccess(updateCheck: Optional<UpdateCheck>) {
						if (updateCheck.isPresent) {
							updateStatusRetrieved(updateCheck.get(), context())
						} else {
							Timber.tag("VaultListPresenter").i("UpdateCheck finished, latest version")
						}
						sharedPreferencesHandler.updateExecuted()
					}

					override fun onError(e: Throwable) {
						showError(e)
					}
				})*/
		} else {
			Timber.tag("VaultListPresenter").i("Update check not started due to no internal connection")
		}
	}

	private fun updateStatusRetrieved(updateCheck: UpdateCheck, context: Context) {
		showNextMessage(updateCheck.releaseNote(), context)
	}

	private fun showNextMessage(message: String, context: Context) {
		/*if (message.isNotEmpty()) {
			view?.showDialog(UpdateAppAvailableDialog.newInstance(message))
		} else {
			view?.showDialog(UpdateAppAvailableDialog.newInstance(context.getText(R.string.dialog_update_available_message).toString()))
		}*/
	}

	fun loadMessageList() {
		messageList
	}

	fun deleteMessage(message: Message) {
		/*deleteVaultUseCase //
			.withVault(vaultModel.toVault()) //
			.run(object : DefaultResultHandler<Long>() {
				override fun onSuccess(vaultId: Long) {
					view?.deleteVaultFromAdapter(vaultId)
				}
			})*/
	}

	private val messageList: Unit
		get() {
			getMessageListUseCase.run(object : DefaultResultHandler<List<Message>>() {
				override fun onSuccess(messages: List<Message>) {
					if (messages.isEmpty()) {
						view?.showEmptyMessagesHint()
					} else {
						view?.hideEmptyMessagesHint()
					}
					view?.renderMessages(messages)
				}
			})
		}

	fun onMessageClicked(message: Message) {
		// TODO
	}

	fun onMessageSettingsClicked(message: Message) {
		view?.showMessagetSettingsDialog(message)
	}

	fun installUpdate() {
		TODO("Not yet implemented")
	}

	fun onAddLocationClicked() {
		view?.showCreateLocationView()
	}

	fun onDisclaimerAccepted() {
		sharedPreferencesHandler.setDisclaimerAccepted()
	}

	init {
		unsubscribeOnDestroy(
			getMessageListUseCase, updateMessagesUseCase
		)
	}
}
