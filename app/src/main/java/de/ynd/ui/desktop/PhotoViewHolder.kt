package de.ynd.ui.desktop

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView

class PhotoViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    private val imageView = view as AppCompatImageView

    fun bind(photo: PhotoItem) {
        imageView.setImageBitmap(photo.bitmap)
    }
}