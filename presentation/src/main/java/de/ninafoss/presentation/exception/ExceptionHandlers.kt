package de.ninafoss.presentation.exception

import android.content.ActivityNotFoundException
import android.content.Context
import java.util.ArrayList
import java.util.Collections
import javax.inject.Inject
import de.ninafoss.domain.di.PerView
import de.ninafoss.presentation.R
import de.ninafoss.presentation.ui.activity.view.View
import timber.log.Timber

@PerView
class ExceptionHandlers @Inject constructor(private val context: Context, defaultExceptionHandler: DefaultExceptionHandler) : Iterable<ExceptionHandler?> {

	private val exceptionHandlers: MutableList<ExceptionHandler> = ArrayList()
	private val defaultExceptionHandler: ExceptionHandler

	private fun setupHandlers() {
		staticHandler(ActivityNotFoundException::class.java, R.string.error_activity_not_found)
	}

	fun handle(view: View, e: Throwable) {
		Timber.tag("ExceptionHandler").d(e, "Unexpected error")
		for (mapping in this) {
			if (mapping.handle(view, e)) {
				return
			}
		}
		defaultExceptionHandler.handle(view, e)
	}

	private fun <T : Throwable> staticHandler(type: Class<T>, messageId: Int) {
		staticHandler(type, context.getString(messageId))
	}

	private fun <T : Throwable> staticHandler(type: Class<T>, message: String) {
		exceptionHandlers.add(object : MessageExceptionHandler<T>(type) {
			override fun toMessage(e: T?): String {
				return if (e?.message?.isNotEmpty() == true) {
					String.format(message, e.message)
				} else message
			}
		})
	}

	override fun iterator(): MutableIterator<ExceptionHandler> {
		return Collections.unmodifiableCollection(exceptionHandlers).iterator()
	}

	init {
		this.defaultExceptionHandler = defaultExceptionHandler
		setupHandlers()
	}
}
