package de.ninafoss.presentation.exception

import android.content.Context
import de.ninafoss.domain.di.PerView
import de.ninafoss.presentation.R
import de.ninafoss.presentation.ui.activity.view.View
import javax.inject.Inject
import timber.log.Timber

@PerView
class DefaultExceptionHandler @Inject constructor(context: Context) : ExceptionHandler() {

	private val defaultMessage: String = context.getString(R.string.error_generic)

	override fun supports(e: Throwable): Boolean {
		return true
	}

	override fun log(e: Throwable) {
		Timber.tag("ExceptionHandler").e(e)
	}

	override fun doHandle(view: View, e: Throwable) {
		view.showError(defaultMessage)
	}

}
