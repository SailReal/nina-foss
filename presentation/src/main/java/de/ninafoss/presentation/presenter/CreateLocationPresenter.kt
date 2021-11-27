package de.ninafoss.presentation.presenter

import android.widget.Toast
import javax.inject.Inject
import de.ninafoss.domain.Location
import de.ninafoss.domain.Message
import de.ninafoss.domain.di.PerView
import de.ninafoss.domain.usecases.NoOpResultHandler
import de.ninafoss.domain.usecases.location.AddOrChangeLocationUseCase
import de.ninafoss.domain.usecases.location.DeleteLocationUseCase
import de.ninafoss.domain.usecases.location.GetAllLocationsUseCase
import de.ninafoss.domain.usecases.location.ListAllPossibleLocationUseCase
import de.ninafoss.domain.usecases.message.UpdateMessagesUseCase
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
	private val updateMessagesUseCase: UpdateMessagesUseCase,
	exceptionMappings: ExceptionHandlers
) : Presenter<CreateLocationView>(exceptionMappings) {

	private val possibleLocations: Map<String, String>? = null

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

	fun onWindowFocusChanged(hasFocus: Boolean) {
		if (hasFocus) {
			loadLocationList()
		}
	}

	fun loadLocationList() {
		locationList
	}

	fun checkUserInput(locationName: String, location: Location?) {
		if(possibleLocations != null) {
			checkUserInput(locationName, location, possibleLocations)
		} else {
			listAllPossibleLocationUseCase.run(object : NoOpResultHandler<Map<String, String>>() {
				override fun onSuccess(locations: Map<String, String>) {
					checkUserInput(locationName, location, locations)
				}

				override fun onError(e: Throwable) {
					showError(e)
				}
			})
		}
	}

	private fun checkUserInput(locationName: String, location: Location?, locations: Map<String, String>) {
		locations.keys.find { key -> key.startsWith(locationName, true) }?.let {
			createLocation(locationName, locations[it]!!, location)
		} ?: view?.showError(R.string.screen_enter_location_name_not_matching_code)
	}

	private fun createLocation(locationName: String, code: String, location: Location?) {
		val countryCode = code.dropLast(7) + "0000000"
		val newLocation = location?.let { Location.aCopyOf(it).withName(locationName).withCode(countryCode).build() }
			?: Location.aLocation().withName(locationName).withCode(countryCode).build()

		addOrChangeLocationUseCase.withLocation(newLocation).run(object : NoOpResultHandler<Void?>() {
			override fun onSuccess(void: Void?) {
				Timber.tag("CreateLocationPresenter").i("Location added or updated")
				Toast.makeText(context(), getString(R.string.notification_location_successfully_added_title), Toast.LENGTH_SHORT).show()
				updateMessages()
			}

			override fun onError(e: Throwable) {
				showError(e)
			}
		})
	}

	private fun updateMessages() {
		updateMessagesUseCase.run(object: NoOpResultHandler<List<Message>>() {
			override fun onFinished() {
				finish()
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

	fun loadPossibleLocations() {
		listAllPossibleLocationUseCase.run(object : NoOpResultHandler<Map<String, String>>() {
			override fun onSuccess(locations: Map<String, String>) {
				view?.setPossibleLocations(locations)
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
			listAllPossibleLocationUseCase,
			updateMessagesUseCase
		)
	}
}
