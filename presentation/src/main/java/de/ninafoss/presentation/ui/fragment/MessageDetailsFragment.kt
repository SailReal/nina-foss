package de.ninafoss.presentation.ui.fragment

import android.graphics.Color
import android.os.Bundle
import de.ninafoss.domain.Message
import de.ninafoss.generator.Fragment
import de.ninafoss.presentation.R
import de.ninafoss.presentation.presenter.MessageDetailsPresenter
import kotlinx.android.synthetic.main.fragment_message_details.*
import javax.inject.Inject

@Fragment(R.layout.fragment_message_details)
class MessageDetailsFragment : BaseFragment() {

	@Inject
	lateinit var messageDetailsPresenter: MessageDetailsPresenter

	override fun setupView() {
        message?.let {
            message_title.text = it.headline
            webView.loadDataWithBaseURL(null, it.message + it.instruction, "text/html", "utf-8", null)
            webView.setBackgroundColor(Color.TRANSPARENT);
        }
	}

    private val message: Message?
        get() = arguments?.getSerializable(ARG_MESSAGE) as? Message

    companion object {

        private const val ARG_MESSAGE = "MESSAGE"

        fun newInstance(message: Message): MessageDetailsFragment {
            val result = MessageDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_MESSAGE, message)
            result.arguments = args
            return result
        }
    }
}
