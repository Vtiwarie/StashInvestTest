package com.stashinvest.stashchallenge.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stashinvest.stashchallenge.api.model.ImageResult
import kotlinx.android.synthetic.main.cell_image_layout.view.*

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(imageResult: ImageResult, listener: () -> Unit) {
        itemView.setOnLongClickListener {
            listener()
            true
        }
        
        Picasso.get()
                .load(imageResult.thumbUri)
                .into(itemView.imageView)
    }
}
