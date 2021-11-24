package de.ninafoss.presentation.ui.activity.view

import de.ninafoss.domain.Message

interface MessageListView : View {

	fun showEmptyMessagesHint()
	fun hideEmptyMessagesHint()
	fun renderMessages(messages: List<Message>)
	fun showMessagetSettingsDialog(message: Message)
	fun showCreateLocationView()

}
