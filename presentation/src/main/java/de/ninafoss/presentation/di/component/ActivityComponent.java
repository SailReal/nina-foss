package de.ninafoss.presentation.di.component;

import android.app.Activity;

import dagger.Component;
import de.ninafoss.domain.di.PerView;
import de.ninafoss.presentation.di.module.ActivityModule;
import de.ninafoss.presentation.ui.activity.CreateLocationActivity;
import de.ninafoss.presentation.ui.activity.MessageDetailsActivity;
import de.ninafoss.presentation.ui.activity.MessageListActivity;
import de.ninafoss.presentation.ui.activity.SettingsActivity;
import de.ninafoss.presentation.ui.activity.SplashActivity;
import de.ninafoss.presentation.ui.fragment.CreateLocationFragment;
import de.ninafoss.presentation.ui.fragment.MessageDetailsFragment;
import de.ninafoss.presentation.ui.fragment.MessageListFragment;

@PerView
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

	Activity activity();

	void inject(SplashActivity splashActivity);

	void inject(CreateLocationActivity createLocationActivity);

	void inject(CreateLocationFragment createLocationFragment);

	void inject(SettingsActivity settingsActivity);

	void inject(MessageListActivity messageListActivity);

	void inject(MessageListFragment messageListFragment);

	void inject(MessageDetailsActivity messageDetailsActivity);

	void inject(MessageDetailsFragment messageDetailsFragment);

}
