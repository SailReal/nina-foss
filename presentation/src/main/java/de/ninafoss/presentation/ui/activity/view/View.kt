package de.ninafoss.presentation.ui.activity.view

import androidx.fragment.app.DialogFragment
import de.ninafoss.presentation.presenter.ActivityHolder
import de.ninafoss.presentation.ui.activity.ErrorDisplay
import de.ninafoss.presentation.ui.activity.MessageDisplay
import de.ninafoss.presentation.ui.activity.ProgressAware
import de.ninafoss.presentation.ui.snackbar.SnackbarAction
import kotlin.reflect.KClass

interface View : ProgressAware, MessageDisplay, ErrorDisplay, ActivityHolder {

	fun showDialog(dialog: DialogFragment)
	fun isShowingDialog(dialog: KClass<out DialogFragment>): Boolean
	fun currentDialog(): DialogFragment?
	fun closeDialog()
	fun finish()
	fun showSnackbar(messageId: Int, action: SnackbarAction)

}
