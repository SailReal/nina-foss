package de.ninafoss.presentation.ui.fragment

import android.view.inputmethod.EditorInfo
import javax.inject.Inject
import de.ninafoss.generator.Fragment
import de.ninafoss.presentation.R
import de.ninafoss.presentation.presenter.CreateLocationPresenter
import kotlinx.android.synthetic.main.fragment_setup_webdav.createCloudButton
import kotlinx.android.synthetic.main.fragment_setup_webdav.passwordEditText
import kotlinx.android.synthetic.main.fragment_setup_webdav.urlPortEditText
import kotlinx.android.synthetic.main.fragment_setup_webdav.userNameEditText

@Fragment(R.layout.fragment_setup_webdav)
class CreateLocationFragment : BaseFragment() {

	@Inject
	lateinit var createLocationPresenter: CreateLocationPresenter

	override fun setupView() {
		createCloudButton.setOnClickListener { createCloud() }
		createCloudButton.setOnEditorActionListener { _, actionId, _ ->
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				createCloud()
			}
			false
		}

		urlPortEditText.text?.length?.let { urlPortEditText.setSelection(it) }
		//showEditableCloudContent(webDavCloudModel)
	}

	/*private fun showEditableCloudContent(webDavCloudModel: WebDavCloudModel?) {
		if (webDavCloudModel != null) {
			urlPortEditText.setText(webDavCloudModel.url())
			userNameEditText.setText(webDavCloudModel.username())
			passwordEditText.setText(getPassword(webDavCloudModel.accessToken()))
			cloudId = webDavCloudModel.id()
			certificate = webDavCloudModel.certificate()
		}
	}*/

	private fun createCloud() {
		val urlPort = urlPortEditText.text.toString().trim()
		val username = userNameEditText.text.toString().trim()
		val password = passwordEditText.text.toString().trim()

		//webDavAddOrChangePresenter.checkUserInput(urlPort, username, password, cloudId, certificate)
	}

	fun hideKeyboard() {
		hideKeyboard(passwordEditText)
	}

	companion object {

		private const val ARG_WEBDAV_CLOUD = "WEBDAV_CLOUD"

		/*fun newInstance(cloudModel: WebDavCloudModel?): WebDavAddOrChangeFragment {
			val result = WebDavAddOrChangeFragment()
			val args = Bundle()
			args.putSerializable(ARG_WEBDAV_CLOUD, cloudModel)
			result.arguments = args
			return result
		}*/


		fun newInstance(): CreateLocationFragment {
			val result = CreateLocationFragment()
			return result
		}
	}

}
