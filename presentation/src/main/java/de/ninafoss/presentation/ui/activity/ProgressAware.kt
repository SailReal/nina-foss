package de.ninafoss.presentation.ui.activity

import de.ninafoss.presentation.model.ProgressModel

interface ProgressAware {

	fun showProgress(progress: ProgressModel)

}
