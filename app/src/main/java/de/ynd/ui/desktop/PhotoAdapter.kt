package de.ynd.ui.desktop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.ynd.R

class PhotoAdapter(
    private var photos: List<PhotoItem>
) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recyclerview_photo, parent, false)
        )


    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    fun newPhotos(newPhotos: List<PhotoItem>) {
        photos = newPhotos
        notifyDataSetChanged() //only for simplify in real app I be using DiffUtil or notifyItemXXX
    }

}