package de.ninafoss.presentation.ui.activity

import javax.inject.Inject
import de.ninafoss.generator.Activity
import de.ninafoss.presentation.presenter.SplashPresenter
import de.ninafoss.presentation.ui.activity.view.SplashView

@Activity(secure = false)
class SplashActivity : BaseActivity(), SplashView {

	@Inject
	lateinit var splashPresenter: SplashPresenter
}
