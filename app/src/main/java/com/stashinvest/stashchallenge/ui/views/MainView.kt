package com.stashinvest.stashchallenge.ui.views

import com.stashinvest.stashchallenge.api.model.ImageResult
import com.stashinvest.stashchallenge.api.model.Metadata
import com.stashinvest.stashchallenge.ui.base.BaseView

interface MainView : BaseView {
    fun showProgess(visible: Boolean)
    fun showDialog(metadata: Metadata, uri: String?, similarImages: ArrayList<ImageResult> = arrayListOf())
}