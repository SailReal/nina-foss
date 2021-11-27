package de.ninafoss.presentation.ui.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import javax.inject.Inject
import de.ninafoss.domain.Location
import de.ninafoss.generator.Fragment
import de.ninafoss.presentation.R
import de.ninafoss.presentation.presenter.CreateLocationPresenter
import de.ninafoss.presentation.ui.adapter.LocationsAdapter
import kotlinx.android.synthetic.main.fragment_create_location.createLocationButton
import kotlinx.android.synthetic.main.fragment_create_location.locationName
import kotlinx.android.synthetic.main.recycler_view_layout.recyclerView

@Fragment(R.layout.fragment_create_location)
class CreateLocationFragment : BaseFragment() {

	@Inject
	lateinit var createLocationPresenter: CreateLocationPresenter

	@Inject
	lateinit var locationsAdapter: LocationsAdapter

	private val onItemClickListener = object : LocationsAdapter.OnItemInteractionListener {
		override fun onLocationSettingsClicked(location: Location) {
			createLocationPresenter.onLocationSettingsClicked(location)
		}
	}

	private val location: Location?
		get() = arguments?.getSerializable(ARG_LOCATION) as? Location

	override fun onResume() {
		super.onResume()
		createLocationPresenter.loadLocationList()
	}

	override fun setupView() {
		setupRecyclerView()

		createLocationButton.setOnClickListener { createLocation() }
		createLocationButton.setOnEditorActionListener { _, actionId, _ ->
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				createLocation()
			}
			false
		}

		locationName.text?.length?.let { locationName.setSelection(it) }
		location?.let { locationName.setText(it.name()) }
	}

	private fun setupRecyclerView() {
		locationsAdapter.setCallback(onItemClickListener)
		recyclerView.layoutManager = LinearLayoutManager(context())
		recyclerView.adapter = locationsAdapter
		recyclerView.setHasFixedSize(true) // smoother scrolling
		recyclerView.setPadding(0, 0, 0, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 88f, resources.displayMetrics).toInt())
		recyclerView.clipToPadding = false
	}

	private fun createLocation() {
		val locationName = locationName.text.toString().trim()
		createLocationPresenter.checkUserInput(locationName, location)
	}

	fun hideKeyboard() {
		hideKeyboard(locationName)
	}

	fun showLocations(locations: List<Location>) {
		locationsAdapter.clear()
		locationsAdapter.addAll(locations)
	}

	fun deleteMessageFromAdapter(locationId: Long) {
		locationsAdapter.deleteMessage(locationId)
	}

	companion object {

		private const val ARG_LOCATION = "LOCATION"

		fun newInstance(location: Location?): CreateLocationFragment {
			val result = CreateLocationFragment()
			val args = Bundle()
			args.putSerializable(ARG_LOCATION, location)
			result.arguments = args
			return result
		}
	}

}
