package de.ninafoss.presentation.ui.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import de.ninafoss.generator.Activity
import de.ninafoss.presentation.NinaFossApp
import de.ninafoss.presentation.R
import de.ninafoss.presentation.di.HasComponent
import de.ninafoss.presentation.di.component.ActivityComponent
import de.ninafoss.presentation.di.component.ApplicationComponent
import de.ninafoss.presentation.di.component.DaggerActivityComponent
import de.ninafoss.presentation.di.module.ActivityModule
import de.ninafoss.presentation.exception.ExceptionHandlers
import de.ninafoss.presentation.model.ProgressModel
import de.ninafoss.presentation.presenter.InstanceStates
import de.ninafoss.presentation.presenter.Presenter
import de.ninafoss.presentation.ui.activity.view.View
import de.ninafoss.presentation.ui.dialog.GenericProgressDialog
import de.ninafoss.presentation.ui.snackbar.SnackbarAction
import de.ninafoss.util.SharedPreferencesHandler
import timber.log.Timber
import java.lang.String.format
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity : AppCompatActivity(), View, ActivityCompat.OnRequestPermissionsResultCallback,
	HasComponent<ActivityComponent> {

	@Inject
	lateinit var exceptionMappings: ExceptionHandlers

	@Inject
	lateinit var sharedPreferencesHandler: SharedPreferencesHandler

	private var activityComponent: ActivityComponent? = null

	private var presenter: Presenter<*>? = null

	private var currentDialog: DialogFragment? = null
	private var closeDialogOnResume: Boolean = false

	/**
	 * Get the Main Application component for dependency injection.
	 *
	 * @return [de.ninafoss.presentation.di.component.ApplicationComponent]
	 */
	private val applicationComponent: ApplicationComponent
		get() = ninaFossApp.component

	private val ninaFossApp: NinaFossApp
		get() = application as NinaFossApp

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		logLifecycle("onCreate")

		this.activityComponent = initializeDagger()

		javaClass.getAnnotation(Activity::class.java)?.let {
			setContentView(getContentLayout(it))

			Activities.setIntent(this)
			this.presenter = Activities.initializePresenter(this)
			afterIntentInjected()

			if (savedInstanceState != null) {
				restoreState(savedInstanceState)
			}

			setupView()
			setupPresenter()

			if (savedInstanceState == null) {
				createAndAddFragment()
			}

			currentDialog = supportFragmentManager.findFragmentByTag(ACTIVE_DIALOG) as? DialogFragment
			closeDialogOnResume = currentDialog != null
		} ?: Timber.tag("BaseActivity").e("Failed to initialize Activity because config is null")
	}

	override fun onStart() {
		super.onStart()
		logLifecycle("onStart")
	}

	override fun onRestart() {
		super.onRestart()
		logLifecycle("onRestart")
	}

	public override fun onResume() {
		super.onResume()
		logLifecycleAsInfo("onResume")

		if (closeDialogOnResume) {
			closeDialog()
		}
		closeDialogOnResume = false
	}

	internal open fun vaultExpectedToBeUnlocked() {
	}

	override fun onResumeFragments() {
		super.onResumeFragments()
		logLifecycle("onResumeFragments")
		presenter?.resume()
	}

	public override fun onPause() {
		super.onPause()
		logLifecycle("onPause")
		presenter?.pause()
	}

	override fun onStop() {
		super.onStop()
		logLifecycle("onStop")
	}

	public override fun onDestroy() {
		super.onDestroy()
		logLifecycle("onDestroy")
		presenter?.destroy()
	}

	private fun getContentLayout(config: Activity): Int {
		return if (config.layout == -1) {
			R.layout.activity_layout
		} else {
			config.layout
		}
	}

	private fun initializeDagger(): ActivityComponent {
		val activityComponent = DaggerActivityComponent.builder()
			.applicationComponent(applicationComponent)
			.activityModule(ActivityModule(this))
			.build()
		Activities.inject(activityComponent, this)
		return activityComponent
	}

	private fun createAndAddFragment() {
		val fragment = createFragment()
		fragment?.let { addFragment(R.id.fragmentContainer, it) }
	}

	private fun afterIntentInjected() {

	}

	internal open fun setupView() {

	}

	internal open fun setupPresenter() {

	}

	internal open fun createFragment(): Fragment? = null

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		val menuResource = getCustomMenuResource()
		if (menuResource != NO_MENU) {
			menuInflater.inflate(menuResource, menu)
			return true
		}
		return super.onCreateOptionsMenu(menu)
	}

	open fun getCustomMenuResource(): Int = NO_MENU

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		onMenuItemSelected(item.itemId)
		return super.onOptionsItemSelected(item)
	}

	internal open fun onMenuItemSelected(itemId: Int): Boolean = false

	/**
	 * Adds a [Fragment] to this activity's layout.
	 *
	 * @param containerViewId The container view to whereClause add the fragment.
	 * @param fragment The fragment to be added.
	 */
	private fun addFragment(containerViewId: Int, fragment: Fragment) {
		val fragmentTransaction = this.supportFragmentManager.beginTransaction()
		fragmentTransaction.add(containerViewId, fragment)
		fragmentTransaction.commit()
	}

	@JvmOverloads
	internal fun replaceFragment(fragment: Fragment, fragmentAnimation: FragmentAnimation, addToBackStack: Boolean = true) {
		val transaction = supportFragmentManager.beginTransaction()
		transaction.setCustomAnimations(fragmentAnimation.enter, fragmentAnimation.exit, fragmentAnimation.popEnter, fragmentAnimation.popExit)
		transaction.replace(R.id.fragmentContainer, fragment)
		if (addToBackStack) {
			transaction.addToBackStack(null)
		}
		transaction.commit()
	}

	override fun getComponent(): ActivityComponent? = activityComponent

	override fun activity(): android.app.Activity = this

	override fun context(): Context = this

	override fun showDialog(dialog: DialogFragment) {
		closeDialog()
		currentDialog = dialog
		dialog.show(supportFragmentManager, ACTIVE_DIALOG)
	}

	override fun isShowingDialog(dialog: KClass<out DialogFragment>): Boolean {
		return if (currentDialog != null) {
			dialog.isInstance(currentDialog)
		} else false
	}

	override fun currentDialog(): DialogFragment? {
		return currentDialog
	}

	override fun onNewIntent(intent: Intent) {
		super.onNewIntent(intent)
		logLifecycle("onNewIntent")
		presenter?.onNewIntent(intent)
	}

	override fun closeDialog() {
		currentDialog?.dismissAllowingStateLoss()
		currentDialog = null
	}

	override fun showMessage(messageId: Int, vararg args: Any) {
		val message = getString(messageId)
		showMessage(message, *args)
	}

	override fun showMessage(message: String, vararg args: Any) {
		val formattedMessage = format(message, *args)
		if (currentDialog is MessageDisplay) {
			(currentDialog as MessageDisplay).showMessage(formattedMessage)
		} else {
			showToastMessage(formattedMessage)
		}
		Timber.tag("Message").i(formattedMessage)
	}

	override fun showProgress(progress: ProgressModel) {
		if (currentDialog is ProgressAware) {
			(currentDialog as ProgressAware).showProgress(progress)
		} else {
			showDialog(GenericProgressDialog.create(progress))
		}
		Timber.tag("Progress").v("%s %d%%", progress.state().name(), progress.progress())
	}

	override fun finish() {
		logLifecycle("finish")
		super.finish()
	}

	override fun showError(messageId: Int) {
		val message = getString(messageId)
		if (currentDialog is ErrorDisplay) {
			(currentDialog as ErrorDisplay).showError(messageId)
		} else {
			showToastMessage(message)
		}
		Timber.tag("Message").w(message)
	}

	override fun showError(message: String) {
		if (currentDialog is ErrorDisplay) {
			(currentDialog as ErrorDisplay).showError(message)
		} else {
			showToastMessage(message)
		}
		Timber.tag("Message").w(message)
	}

	override fun showSnackbar(messageId: Int, action: SnackbarAction) {
		//Snackbar.make(snackbarView(), messageId, Snackbar.LENGTH_INDEFINITE).setAction(action.text, action).show()
	}

	/*internal open fun snackbarView(): android.view.View {
		return activity().findViewById(R.id.locationsRecyclerView) as android.view.View?
			?: return activity().findViewById(R.id.coordinatorLayout)
	}*/

	internal fun getCurrentFragment(fragmentContainer: Int): Fragment? = supportFragmentManager.findFragmentById(fragmentContainer)


	override fun onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)
		if (newConfig.orientation == ORIENTATION_LANDSCAPE) {
			logLifecycle("onConfigurationChanged: landscape")
		} else if (newConfig.orientation == ORIENTATION_PORTRAIT) {
			logLifecycle("onConfigurationChanged: portrait")
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		logLifecycle("onSaveInstanceState")
		saveState(outState)
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		super.onRestoreInstanceState(savedInstanceState)
		logLifecycle("onRestoreInstanceState")
		restoreState(savedInstanceState)
	}

	private fun saveState(state: Bundle) {
		InstanceStates.save(presenter, state)
	}

	private fun restoreState(state: Bundle) {
		InstanceStates.restore(presenter, state)
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		//presenter?.onRequestPermissionsResult(requestCode, permissions, grantResults)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
		super.onActivityResult(requestCode, resultCode, intent)
		presenter?.onActivityResult(requestCode, resultCode, intent)
	}

	private fun logLifecycle(method: String) {
		Timber.tag("ActivityLifecycle").d("%s %s", method, this)
	}

	private fun logLifecycleAsInfo(method: String) {
		Timber.tag("ActivityLifecycle").i("%s %s", method, this)
	}

	private fun showToastMessage(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
	}

	internal enum class FragmentAnimation constructor(
		val enter: Int,
		val exit: Int,
		val popEnter: Int,
		val popExit: Int
	) {

		NAVIGATE_IN_TO_FOLDER(R.animator.enter_from_right, R.animator.exit_to_left, R.animator.enter_from_left, R.animator.exit_to_right), //
		NAVIGATE_OUT_OF_FOLDER(R.animator.enter_from_left, R.animator.exit_to_right, R.animator.enter_from_right, R.animator.exit_to_left)
	}

	companion object {

		const val NO_MENU = -1
		private const val ACTIVE_DIALOG = "activeDialog"
	}
}
