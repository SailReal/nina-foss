package de.ninafoss.presentation.presenter

import android.content.Context
import android.net.Uri
import de.ninafoss.domain.usecases.location.DataSource
import de.ninafoss.presentation.util.ContentResolverUtil
import java.io.IOException
import java.io.InputStream

class UriBasedDataSource private constructor(private val uri: Uri) : DataSource {

	override fun size(context: Context): Long? {
		return ContentResolverUtil(context).fileSize(uri)
	}

	@Throws(IOException::class)
	override fun open(context: Context): InputStream? {
		return ContentResolverUtil(context).openInputStream(uri)
	}

	override fun decorate(delegate: DataSource): DataSource {
		return delegate
	}

	@Throws(IOException::class)
	override fun close() {
		// do nothing
	}

	companion object {

		@JvmStatic
		fun from(uri: Uri): UriBasedDataSource {
			return UriBasedDataSource(uri)
		}
	}
}
