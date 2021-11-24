package de.ninafoss.presentation.intent;

import de.ninafoss.domain.Location;
import de.ninafoss.generator.Intent;
import de.ninafoss.generator.Optional;
import de.ninafoss.presentation.ui.activity.CreateLocationActivity;

@Intent(CreateLocationActivity.class)
public interface CreateLocationIntent {

	@Optional
	Location location();

}
