package de.ninafoss.presentation.presenter

import javax.inject.Inject
import de.ninafoss.domain.di.PerView
import de.ninafoss.presentation.R
import de.ninafoss.presentation.exception.ExceptionHandlers
import de.ninafoss.presentation.ui.activity.view.CreateLocationView

@PerView
class CreateLocationPresenter @Inject constructor(
	exceptionMappings: ExceptionHandlers
) : Presenter<CreateLocationView>(exceptionMappings) {

	fun onCreateVaultClicked(vaultName: String) {
		when {
			vaultName.isEmpty() -> {
				view?.showMessage(R.string.screen_enter_vault_name_msg_name_empty)
			}
			else -> {
				finishWithResult(vaultName)
			}
		}
	}
}
