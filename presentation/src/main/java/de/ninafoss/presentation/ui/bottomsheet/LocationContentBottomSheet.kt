package de.ninafoss.presentation.ui.bottomsheet

import android.os.Bundle
import de.ninafoss.domain.Location
import de.ninafoss.generator.BottomSheet
import de.ninafoss.presentation.R
import kotlinx.android.synthetic.main.dialog_bottom_sheet_location_action.deleteLocation
import kotlinx.android.synthetic.main.dialog_bottom_sheet_location_action.title

@BottomSheet(R.layout.dialog_bottom_sheet_location_action)
class SettingsLocationBottomSheet : BaseBottomSheet<SettingsLocationBottomSheet.Callback>() {

	interface Callback {

		fun onDeleteLocationClicked(location: Location)

	}

	override fun setupView() {
		val location = requireArguments().getSerializable(LOCATION_ARG) as Location

		title.text = String.format(getString(R.string.screen_location_bottom_sheet_actions_title), location.name())

		deleteLocation.setOnClickListener {
			callback?.onDeleteLocationClicked(location)
			dismiss()
		}
	}

	companion object {

		private const val LOCATION_ARG = "location"
		fun newInstance(location: Location): SettingsLocationBottomSheet {
			val dialog = SettingsLocationBottomSheet()
			val args = Bundle()
			args.putSerializable(LOCATION_ARG, location)
			dialog.arguments = args
			return dialog
		}
	}
}
