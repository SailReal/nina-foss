package de.ninafoss.presentation.ui.fragment

import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import javax.inject.Inject
import de.ninafoss.domain.Message
import de.ninafoss.generator.Fragment
import de.ninafoss.presentation.R
import de.ninafoss.presentation.presenter.MessageListPresenter
import de.ninafoss.presentation.ui.adapter.MessagesAdapter
import kotlinx.android.synthetic.main.fragment_message_list.coordinatorLayout
import kotlinx.android.synthetic.main.fragment_message_list.floating_action_button
import kotlinx.android.synthetic.main.recycler_view_layout.recyclerView

@Fragment(R.layout.fragment_message_list)
class MessageListFragment : BaseFragment() {

	@Inject
	lateinit var messageListPresenter: MessageListPresenter

	@Inject
	lateinit var messagesAdapter: MessagesAdapter

	private val onItemClickListener = object : MessagesAdapter.OnItemInteractionListener {
		override fun onMessageClicked(message: Message) {
			messageListPresenter.onMessageClicked(message)
		}

		override fun onMessageSettingsClicked(message: Message) {
			messageListPresenter.onMessageSettingsClicked(message)
		}
	}

	override fun setupView() {
		setupRecyclerView()
		floating_action_button.setOnClickListener { messageListPresenter.onAddLocationClicked() }
	}

	override fun onResume() {
		super.onResume()
		messageListPresenter.loadMessageList()
	}

	private fun setupRecyclerView() {
		messagesAdapter.setCallback(onItemClickListener)
		recyclerView.layoutManager = LinearLayoutManager(context())
		recyclerView.adapter = messagesAdapter
		recyclerView.setHasFixedSize(true) // smoother scrolling
		recyclerView.setPadding(0, 0, 0, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 88f, resources.displayMetrics).toInt())
		recyclerView.clipToPadding = false
	}

	fun showMessages(vaultModelCollection: List<Message>?) {
		messagesAdapter.clear()
		messagesAdapter.addAll(vaultModelCollection)
	}

	fun deleteMessageFromAdapter(messageId: Long) {
		messagesAdapter.deleteMessage(messageId)
	}

	fun addOrUpdateMessage(message: Message?) {
		messagesAdapter.addOrUpdateMessage(message)
	}

	fun rootView(): View = coordinatorLayout
}
