package de.ninafoss.presentation.ui.dialog

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import de.ninafoss.generator.Dialog
import de.ninafoss.presentation.R

@Dialog(R.layout.dialog_disclaimer_confirmation)
class DisclaimerDialog : BaseDialog<DisclaimerDialog.Callback>() {

	interface Callback {

		fun onDisclaimerAccepted()

	}

	public override fun setupDialog(builder: AlertDialog.Builder): android.app.Dialog {
		return builder //
			.setTitle(R.string.dialog_disclaimer_confirmation_title) //
			.setNeutralButton(getString(R.string.dialog_disclaimer_neutral_button)) { _: DialogInterface, _: Int -> callback?.onDisclaimerAccepted() } //
			.create()
	}

	public override fun setupView() {
		// empty
	}

	companion object {

		fun newInstance(): DialogFragment {
			return DisclaimerDialog()
		}
	}
}
