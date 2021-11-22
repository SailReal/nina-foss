package de.ninafoss.presentation

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import de.ninafoss.data.repository.RepositoryModule
import de.ninafoss.presentation.di.component.ApplicationComponent
import de.ninafoss.presentation.di.component.DaggerApplicationComponent
import de.ninafoss.presentation.di.module.ApplicationModule
import de.ninafoss.presentation.di.module.ThreadModule
import de.ninafoss.presentation.logging.CrashLogging.Companion.setup
import de.ninafoss.presentation.logging.DebugLogger
import de.ninafoss.presentation.logging.ReleaseLogger
import de.ninafoss.util.SharedPreferencesHandler
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class NinaFossApp : MultiDexApplication(), de.ninafoss.presentation.di.HasComponent<ApplicationComponent> {

	private lateinit var applicationComponent: ApplicationComponent

	override fun onCreate() {
		super.onCreate()
		setupLogging()
		val flavor = when (BuildConfig.FLAVOR) {
			"apkstore" -> {
				"APK Store Edition"
			}
			"fdroid" -> {
				"F-Droid Edition"
			}
			else -> "Google Play Edition"
		}
		Timber.tag("App").i(
			"Nina FOSS v%s (%d) \"%s\" started on android %s / API%d using a %s",  //
			BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, flavor,  //
			Build.VERSION.RELEASE, Build.VERSION.SDK_INT,  //
			Build.MODEL
		)
		Timber.tag("App").d("appId %s", BuildConfig.APPLICATION_ID)

		initializeInjector()
		AppCompatDelegate.setDefaultNightMode(SharedPreferencesHandler(applicationContext()).screenStyleMode)
		cleanupCache()

		RxJavaPlugins.setErrorHandler { e: Throwable? -> Timber.tag("NinaFossApp").e(e, "BaseErrorHandler detected a problem") }
	}

	private fun setupLogging() {
		setupLoggingFramework()
		setup()
	}

	private fun initializeInjector() {
		applicationComponent = DaggerApplicationComponent.builder() //
			.applicationModule(ApplicationModule(this)) //
			.threadModule(ThreadModule()) //
			.repositoryModule(RepositoryModule()) //
			.build()
	}

	private fun cleanupCache() {
		CacheCleanupTask(applicationComponent.fileUtil()).execute()
	}

	private fun setupLoggingFramework() {
		if (BuildConfig.DEBUG) {
			Timber.plant(DebugLogger())
		}
		Timber.plant(ReleaseLogger(Companion.applicationContext))
	}

	override fun getComponent(): ApplicationComponent {
		return applicationComponent
	}

	companion object {

		private lateinit var applicationContext: Context
		fun applicationContext(): Context {
			return applicationContext
		}
	}

	init {
		Companion.applicationContext = this
	}
}
