package com.stashinvest.stashchallenge.ui.viewmodel

import android.view.View
import com.stashinvest.stashchallenge.R
import com.stashinvest.stashchallenge.api.model.ImageResult
import com.stashinvest.stashchallenge.ui.viewholder.ImageViewHolder
import com.stashinvest.stashchallenge.ui.viewmodel.ViewModelType.STASH_IMAGE

class ImageViewModel(private val imageResult: ImageResult,
                     private val listener: (id: String, uri: String?) -> Unit)
    : BaseViewModel<ImageViewHolder>(R.layout.cell_image_layout) {
    
    override fun createItemViewHolder(view: View): ImageViewHolder {
        return ImageViewHolder(view)
    }
    
    override fun bindItemViewHolder(holder: ImageViewHolder) {
        holder.bind(imageResult) { listener(imageResult.id, imageResult.thumbUri) }
    }
    
    override val viewType: ViewModelType
        get() = STASH_IMAGE
}
