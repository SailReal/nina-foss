package de.ninafoss.presentation.ui.adapter

import android.view.View
import de.ninafoss.domain.Message
import de.ninafoss.presentation.R
import kotlinx.android.synthetic.main.item_message.view.*
import javax.inject.Inject

class MessagesAdapter @Inject
internal constructor() : RecyclerViewBaseAdapter<Message, MessagesAdapter.OnItemInteractionListener, MessagesAdapter.MessageViewHolder>() {

	interface OnItemInteractionListener {

		fun onMessageClicked(message: Message)

		fun onMessageSettingsClicked(message: Message)
	}

	override fun getItemLayout(viewType: Int): Int {
		return R.layout.item_message
	}

	override fun createViewHolder(view: View, viewType: Int): MessageViewHolder {
		return MessageViewHolder(view)
	}

	fun deleteMessage(messageId: Long) {
		deleteItem(getMessage(messageId))
	}

	fun addOrUpdateMessage(message: Message?) {
		if (contains(message)) {
			replaceItem(message)
		} else {
			addItem(message)
		}
	}

	private fun getMessage(messageId: Long): Message? {
		return itemCollection.firstOrNull { it.id == messageId }
	}

	inner class MessageViewHolder(itemView: View) : RecyclerViewBaseAdapter<*, *, *>.ItemViewHolder(itemView) {

		override fun bind(position: Int) {
			val message = getItem(position)

			itemView.messageName.text = message.headline

			//itemView.cloudImage.setImageResource(message.cloudType.vaultImageResource)

			itemView.setOnClickListener {
				//itemView.cloudImage.setImageResource(message.cloudType.vaultSelectedImageResource)
				callback.onMessageClicked(message)
			}

			itemView.settings.setOnClickListener {
				//itemView.cloudImage.setImageResource(message.cloudType.vaultSelectedImageResource)
				callback.onMessageSettingsClicked(message)
			}
		}
	}
}
