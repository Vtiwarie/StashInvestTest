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
        fetchSearch(searchPhrase)
    }

    fun onImageLongPress(id: String, uri: String?) {
        fetchImageMetadata(id, uri)
    }

    private fun updateImages(images: List<ImageResult>) {
        val viewModels = images.map { imageFactory.createImageViewModel(it, ::onImageLongPress) }
        adapter.setViewModels(viewModels)
    }

    /*
    Normally would place this in a repository, kept here for simplicity
    */
    private fun fetchSearch(searchPhrase: String) {
        view?.showProgess(true)
        disposables += stashImageService.searchImages(searchPhrase)
                .doOnError {
                    view?.showError(it)
                }
                .subscribe { response ->
                    view?.showProgess(false)

                    val images = response.images
                    updateImages(images)
                }
    }

    /*
     Normally would place this in a repository, kept here for simplicity
     */
    private fun fetchImageMetadata(id: String, uri: String?) {
        disposables += stashImageService.getImageMetadata(id)
                .doOnError {
                    view?.showError(it)
                }
                .subscribe { response ->
                    val metadata = response.metadata.firstOrNull()
                    metadata?.let {
                        view?.showDialog(metadata, uri)
                    }
                }

    }
}