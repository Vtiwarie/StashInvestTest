package com.stashinvest.stashchallenge.ui.presenter

import com.stashinvest.stashchallenge.api.StashImageService
import com.stashinvest.stashchallenge.api.model.ImageResult
import com.stashinvest.stashchallenge.ui.adapter.ViewModelAdapter
import com.stashinvest.stashchallenge.ui.base.BasePresenter
import com.stashinvest.stashchallenge.ui.factory.ImageFactory
import com.stashinvest.stashchallenge.ui.views.MainView
import javax.inject.Inject

/**
 * @author Vishaan Tiwarie
 *
 * MainFragment Presenter
 */
class MainPresenter @Inject constructor(
        var adapter: ViewModelAdapter,
        var stashImageService: StashImageService,
        var imageFactory: ImageFactory
) : BasePresenter<MainView>() {

    fun search(searchPhrase: String) {
        view?.showProgess(true)
        stashImageService.searchImages(searchPhrase)
                .doOnError { error ->
                    view?.showError(error)
                }
                .subscribe { response ->
                    view?.showProgess(false)

                    val images = response.images
                    updateImages(images)
                }
    }

    fun updateImages(images: List<ImageResult>) {
        val viewModels = images.map { imageFactory.createImageViewModel(it, ::onImageLongPress) }
        adapter.setViewModels(viewModels)
    }

    fun onImageLongPress(id: String, uri: String?) {
        //todo - implement new feature
    }
}