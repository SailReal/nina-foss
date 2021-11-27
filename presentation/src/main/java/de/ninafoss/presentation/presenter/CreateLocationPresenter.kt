package de.ninafoss.presentation.presenter

import javax.inject.Inject
import de.ninafoss.domain.Location
import de.ninafoss.domain.di.PerView
import de.ninafoss.domain.usecases.NoOpResultHandler
import de.ninafoss.domain.usecases.location.AddOrChangeLocationUseCase
import de.ninafoss.domain.usecases.location.DeleteLocationUseCase
import de.ninafoss.domain.usecases.location.GetAllLocationsUseCase
import de.ninafoss.domain.usecases.location.ListAllPossibleLocationUseCase
import de.ninafoss.presentation.R
import de.ninafoss.presentation.exception.ExceptionHandlers
import de.ninafoss.presentation.ui.activity.view.CreateLocationView
import timber.log.Timber

@PerView
class CreateLocationPresenter @Inject constructor(
	private val addOrChangeLocationUseCase: AddOrChangeLocationUseCase,
	private val getAllLocationsUseCase: GetAllLocationsUseCase,
	private val listAllPossibleLocationUseCase: ListAllPossibleLocationUseCase,
	private val deleteLocationUseCase: DeleteLocationUseCase,
	exceptionMappings: ExceptionHandlers
) : Presenter<CreateLocationView>(exceptionMappings) {

	fun onWindowFocusChanged(hasFocus: Boolean) {
		if (hasFocus) {
			loadLocationList()
		}
	}

	private val locationList: Unit
		get() {
			getAllLocationsUseCase.run(object : DefaultResultHandler<List<Location>>() {
				override fun onSuccess(locations: List<Location>) {
					/*if (locations.isEmpty()) {
						view?.showEmptyMessagesHint()
					} else {
						view?.hideEmptyMessagesHint()
					}*/
					view?.render(locations)
				}
			})
		}

	fun loadLocationList() {
		locationList
	}

	fun checkUserInput(locationName: String, location: Location?) {
		listAllPossibleLocationUseCase.run(object : NoOpResultHandler<Map<String, String>>() {
			override fun onSuccess(locations: Map<String, String>) {
				locations.keys.find { key -> key.startsWith(locationName, true) }?.let {
					createLocation(locationName, locations[it]!!, location)
				} ?: view?.showError(R.string.screen_enter_location_name_not_matching_code)
			}

			override fun onError(e: Throwable) {
				showError(e)
			}
		})
	}

	private fun createLocation(locationName: String, code: String, location: Location?) {
		val countryCode = code.dropLast(7) + "0000000"
		val newLocation = location?.let { Location.aCopyOf(it).withName(locationName).withCode(countryCode).build() }
			?: Location.aLocation().withName(locationName).withCode(countryCode).build()

		addOrChangeLocationUseCase.withLocation(newLocation).run(object : NoOpResultHandler<Void?>() {
			override fun onSuccess(void: Void?) {
				Timber.tag("CreateLocationPresenter").i("Location added or updated")
				finishWithResult(newLocation)
			}

			override fun onError(e: Throwable) {
				showError(e)
			}
		})
	}

	fun onLocationSettingsClicked(location: Location) {
		view?.showLocationSettingsDialog(location)
	}

	fun deleteLocation(location: Location) {
		deleteLocationUseCase.withLocation(location).run(object : NoOpResultHandler<Void?>() {
			override fun onSuccess(void: Void?) {
				Timber.tag("CreateLocationPresenter").i("Location removed")
				loadLocationList()
			}

			override fun onError(e: Throwable) {
				showError(e)
			}
		})
	}

	init {
		unsubscribeOnDestroy(
			addOrChangeLocationUseCase,
			deleteLocationUseCase,
			getAllLocationsUseCase,
			listAllPossibleLocationUseCase
		)
	}
}
