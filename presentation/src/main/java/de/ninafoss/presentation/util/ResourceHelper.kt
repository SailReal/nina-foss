package de.ninafoss.presentation.util

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import de.ninafoss.presentation.NinaFossApp

class ResourceHelper {

	companion object {

		fun getString(resId: Int): String {
			return NinaFossApp.applicationContext().getString(resId)
		}

		fun getColor(colorId: Int): Int {
			return ContextCompat.getColor(NinaFossApp.applicationContext(), colorId)
		}

		fun getDrawable(drawId: Int): Drawable? {
			return ContextCompat.getDrawable(NinaFossApp.applicationContext(), drawId)
		}

		fun getPixelOffset(dimenId: Int): Int {
			return NinaFossApp.applicationContext().resources.getDimensionPixelOffset(dimenId)
		}
	}
}
