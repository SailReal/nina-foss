package de.ninafoss.presentation.presenter

import javax.inject.Inject
import de.ninafoss.domain.Location
import de.ninafoss.domain.di.PerView
import de.ninafoss.domain.usecases.NoOpResultHandler
import de.ninafoss.domain.usecases.location.AddOrChangeLocationUseCase
import de.ninafoss.presentation.R
import de.ninafoss.presentation.exception.ExceptionHandlers
import de.ninafoss.presentation.ui.activity.view.CreateLocationView
import timber.log.Timber

@PerView
class CreateLocationPresenter @Inject constructor(
	private val addOrChangeLocationUseCase: AddOrChangeLocationUseCase,
	exceptionMappings: ExceptionHandlers
) : Presenter<CreateLocationView>(exceptionMappings) {

	fun checkUserInput(locationName: String, location: Location?) {
		// just for testing and until location parsing is implemented
		when (locationName) {
			"Königsfeld" -> {
				createLocation(locationName, location)
			}
			"Heidelberg" -> {
				createLocation(locationName, location)
			}
			else -> {
				view?.showError(R.string.screen_enter_location_name_not_matching_code)
			}
		}
	}

	private fun createLocation(locationName: String, location: Location?) {
		when {
			locationName.isEmpty() -> {
				view?.showMessage(R.string.screen_enter_location_name_msg_name_empty)
			}
			else -> {
				val newLocation = location?.let { Location.aCopyOf(it).withName(locationName).withCode(getCodeFromName(locationName)).build() }
					?: Location.aLocation().withName(locationName).withCode(getCodeFromName(locationName)).build()

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
		}
	}

	private fun getCodeFromName(locationName: String): String? {
		return "083260000000"
	}

	init {
		unsubscribeOnDestroy(
			addOrChangeLocationUseCase
		)
	}
}
