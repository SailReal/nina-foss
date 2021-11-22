package de.ninafoss.presentation.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import javax.inject.Inject
import de.ninafoss.util.file.MimeType
import de.ninafoss.util.file.MimeTypes
import timber.log.Timber

class FileUtil @Inject constructor(private val context: Context, private val mimeTypes: MimeTypes) {

	private var decryptedFileStorage: File = File(context.cacheDir, "decrypted")

	fun cleanup() {
		cleanupDir(context.cacheDir)
	}

	private fun cleanupDir(directory: File) {
		directory.listFiles()?.forEach { child ->
			if (child.isDirectory) {
				cleanupDir(child)
			} else {
				cleanupFile(child)
			}
		}
	}

	private fun cleanupFile(file: File) {
		val cleanupFilesOlderThan = System.currentTimeMillis() - CLEANUP_AFTER_MILLISECONDS
		if (file.lastModified() < cleanupFilesOlderThan) {
			if (!file.delete()) {
				Timber.w("Failed to cleanup file in cacheDir")
			}
		}
	}

	fun contentUriForNewTempFile(fileName: String): Uri {
		decryptedFileStorage.mkdir()
		val file = File(decryptedFileStorage, fileName)
		return FileProvider.getUriForFile(context, context.applicationContext.packageName + ".fileprovider", file)
	}

	fun tempFile(fileName: String): File {
		decryptedFileStorage.mkdir()
		return File(decryptedFileStorage, fileName)
	}

	fun fileInfo(name: String): FileInfo {
		return FileInfo(name, mimeTypes)
	}

	class FileInfo(val name: String, mimeTypes: MimeTypes) {

		var extension: String?
		var mimeType: MimeType

		init {
			val lastDot = name.lastIndexOf('.')
			if (lastDot == -1 || lastDot == name.length - 1) {
				extension = null
				mimeType = MimeType.APPLICATION_OCTET_STREAM
			} else {
				extension = name.substring(lastDot + 1)
				mimeType = extension?.let { mimeTypes.fromExtension(it) } ?: MimeType.APPLICATION_OCTET_STREAM
			}
		}
	}

	companion object {

		private const val MILLISECONDS_PER_HOUR = 60 * 60 * 1000L
		private const val CLEANUP_AFTER_MILLISECONDS = 5 * MILLISECONDS_PER_HOUR

		private fun getSimpleFileName(fileName: String): String {
			val extensionSeparatorIndex = fileName.lastIndexOf(".")
			return if (extensionSeparatorIndex != -1) fileName.substring(0, extensionSeparatorIndex) else fileName
		}

		fun getExtension(fileName: String): String? {
			val extensionSeparatorIndex = fileName.lastIndexOf(".")
			return if (extensionSeparatorIndex != -1) fileName.substring(extensionSeparatorIndex + 1) else null
		}
	}

}
