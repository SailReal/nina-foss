package de.ninafoss.presentation.ui.activity.view

import de.ninafoss.domain.Location

interface CreateLocationView : View {

	fun render(locations: List<Location>)
	fun showLocationSettingsDialog(location: Location)

}
