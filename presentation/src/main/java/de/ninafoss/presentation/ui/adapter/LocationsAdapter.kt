package de.ninafoss.presentation.ui.adapter

import android.view.View
import javax.inject.Inject
import de.ninafoss.domain.Location
import de.ninafoss.presentation.R
import kotlinx.android.synthetic.main.item_location.view.locationName
import kotlinx.android.synthetic.main.item_location.view.settings

class LocationsAdapter @Inject
internal constructor() : RecyclerViewBaseAdapter<Location, LocationsAdapter.OnItemInteractionListener, LocationsAdapter.LocationViewHolder>() {

	interface OnItemInteractionListener {

		fun onLocationSettingsClicked(location: Location)

	}

	override fun getItemLayout(viewType: Int): Int {
		return R.layout.item_location
	}

	override fun createViewHolder(view: View, viewType: Int): LocationViewHolder {
		return LocationViewHolder(view)
	}

	fun deleteMessage(messageId: Long) {
		deleteItem(getLocation(messageId))
	}

	fun addOrUpdateMessage(message: Location?) {
		if (contains(message)) {
			replaceItem(message)
		} else {
			addItem(message)
		}
	}

	private fun getLocation(locationId: Long): Location? {
		return itemCollection.firstOrNull { it.id() == locationId }
	}

	inner class LocationViewHolder(itemView: View) : RecyclerViewBaseAdapter<*, *, *>.ItemViewHolder(itemView) {

		override fun bind(position: Int) {
			val location = getItem(position)

			itemView.locationName.text = location.name()

			//itemView.cloudImage.setImageResource(message.cloudType.vaultImageResource)

			itemView.settings.setOnClickListener {
				callback.onLocationSettingsClicked(location)
			}
		}
	}
}
