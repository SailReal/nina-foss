package de.ninafoss.presentation.ui.snackbar

import android.view.View

interface SnackbarAction : View.OnClickListener {

	val text: Int

}
