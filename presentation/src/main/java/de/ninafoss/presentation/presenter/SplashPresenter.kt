package de.ninafoss.presentation.presenter

import javax.inject.Inject
import de.ninafoss.domain.di.PerView
import de.ninafoss.presentation.exception.ExceptionHandlers
import de.ninafoss.presentation.intent.Intents
import de.ninafoss.presentation.ui.activity.view.SplashView

@PerView
class SplashPresenter @Inject constructor(exceptionMappings: ExceptionHandlers) : Presenter<SplashView>(exceptionMappings) {

	override fun resumed() {
		Intents.messageListIntent().startActivity(this)
		finish()
	}
}
