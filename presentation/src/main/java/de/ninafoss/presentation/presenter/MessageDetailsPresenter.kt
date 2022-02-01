package de.ninafoss.presentation.presenter

import de.ninafoss.domain.di.PerView
import de.ninafoss.presentation.exception.ExceptionHandlers
import de.ninafoss.presentation.ui.activity.view.MessageDetailsView
import javax.inject.Inject

@PerView
class MessageDetailsPresenter @Inject constructor(
	exceptionMappings: ExceptionHandlers
) : Presenter<MessageDetailsView>(exceptionMappings) {

	init {
		//unsubscribeOnDestroy()
	}
}
