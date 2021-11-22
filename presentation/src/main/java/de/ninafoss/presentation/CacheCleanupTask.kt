package de.ninafoss.presentation

import android.os.AsyncTask
import de.ninafoss.presentation.util.FileUtil

class CacheCleanupTask(private val fileUtil: FileUtil) : AsyncTask<Void, Void, Unit>() {

	override fun doInBackground(vararg params: Void) {
		fileUtil.cleanup()
	}

}
