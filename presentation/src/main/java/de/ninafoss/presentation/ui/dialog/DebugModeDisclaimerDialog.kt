package de.ninafoss.presentation.ui.dialog

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import de.ninafoss.generator.Dialog
import de.ninafoss.presentation.R

@Dialog(R.layout.dialog_debug_mode_disclaimer)
class DebugModeDisclaimerDialog : BaseDialog<DebugModeDisclaimerDialog.Callback>() {

	interface Callback {

		fun onDisclaimerAccepted()
		fun onDisclaimerRejected()
	}

	public override fun setupDialog(builder: AlertDialog.Builder): android.app.Dialog {
		return builder //
			.setTitle(R.string.dialog_debug_mode_disclaimer_title) //
			.setPositiveButton(getString(R.string.dialog_debug_mode_positive_button)) { _: DialogInterface, _: Int -> callback?.onDisclaimerAccepted() } //
			.setNegativeButton(getString(R.string.dialog_debug_mode_negative_button)) { _: DialogInterface, _: Int -> callback?.onDisclaimerRejected() } //
			.create()
	}

	public override fun setupView() {
		// empty
	}

	override fun onCancel(dialog: DialogInterface) {
		super.onCancel(dialog)
		callback?.onDisclaimerRejected()
	}

	companion object {

		fun newInstance(): DialogFragment {
			return DebugModeDisclaimerDialog()
		}
	}
}
