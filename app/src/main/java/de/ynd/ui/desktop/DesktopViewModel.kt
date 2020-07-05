package de.ynd.ui.desktop

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import timber.log.Timber
import javax.inject.Inject

class DesktopViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    fun onNewPhoto(bitmap: Bitmap?) {
        Timber.d("Photo is in bitmap ${bitmap?.width}/${bitmap?.height}")
    }

}