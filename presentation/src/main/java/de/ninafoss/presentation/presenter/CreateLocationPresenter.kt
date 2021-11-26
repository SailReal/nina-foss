package de.ninafoss.presentation.presenter

import javax.inject.Inject
import de.ninafoss.domain.Location
import de.ninafoss.domain.di.PerView
import de.ninafoss.domain.usecases.NoOpResultHandler
import de.ninafoss.domain.usecases.location.AddOrChangeLocationUseCase
import de.ninafoss.domain.usecases.location.ListAllPossibleLocationUseCase
import de.ninafoss.presentation.R
import de.ninafoss.presentation.exception.ExceptionHandlers
import de.ninafoss.presentation.ui.activity.view.CreateLocationView
import timber.log.Timber

@PerView
class CreateLocationPresenter @Inject constructor(
	private val addOrChangeLocationUseCase: AddOrChangeLocationUseCase,
	private val listAllPossibleLocationUseCase: ListAllPossibleLocationUseCase,
	exceptionMappings: ExceptionHandlers
) : Presenter<CreateLocationView>(exceptionMappings) {

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

	init {
		unsubscribeOnDestroy(
			addOrChangeLocationUseCase,
			listAllPossibleLocationUseCase
		)
	}
}
