package com.stashinvest.stashchallenge.ui.factory

import com.stashinvest.stashchallenge.api.model.ImageResult
import com.stashinvest.stashchallenge.ui.viewmodel.ImageViewModel

import javax.inject.Inject

class ImageFactory @Inject constructor() {
    fun createImageViewModel(imageResult: ImageResult,
                             listener: (id: String, uri: String?) -> Unit): ImageViewModel {
        return ImageViewModel(imageResult, listener)
    }
}
