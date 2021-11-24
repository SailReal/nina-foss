package de.ninafoss.presentation.ui.fragment

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import javax.inject.Inject
import de.ninafoss.domain.Location
import de.ninafoss.generator.Fragment
import de.ninafoss.presentation.R
import de.ninafoss.presentation.presenter.CreateLocationPresenter
import kotlinx.android.synthetic.main.fragment_create_location.createLocationButton
import kotlinx.android.synthetic.main.fragment_create_location.locationName

@Fragment(R.layout.fragment_create_location)
class CreateLocationFragment : BaseFragment() {

	@Inject
	lateinit var createLocationPresenter: CreateLocationPresenter

	private val location: Location?
		get() = arguments?.getSerializable(ARG_LOCATION) as? Location

	override fun setupView() {
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

	private fun createLocation() {
		val locationName = locationName.text.toString().trim()
		createLocationPresenter.checkUserInput(locationName, location)
	}

	fun hideKeyboard() {
		hideKeyboard(locationName)
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
