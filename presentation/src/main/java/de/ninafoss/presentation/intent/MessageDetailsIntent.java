package de.ninafoss.presentation.intent;

import de.ninafoss.domain.Message;
import de.ninafoss.generator.Intent;
import de.ninafoss.presentation.ui.activity.MessageDetailsActivity;

@Intent(MessageDetailsActivity.class)
public interface MessageDetailsIntent {

	Message message();

}
