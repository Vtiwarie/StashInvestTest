package com.stashinvest.stashchallenge.ui.presenter

import com.stashinvest.stashchallenge.api.StashImageService
import com.stashinvest.stashchallenge.api.StashImageService.Companion.RxSchedulers
import com.stashinvest.stashchallenge.api.model.ImageResult
import com.stashinvest.stashchallenge.ui.adapter.ViewModelAdapter
import com.stashinvest.stashchallenge.ui.base.BasePresenter
import com.stashinvest.stashchallenge.ui.factory.ImageFactory
import com.stashinvest.stashchallenge.ui.views.MainView
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * @author Vishaan Tiwarie
 *
 * MainFragment Presenter
 */
class MainPresenter @Inject constructor() : BasePresenter<MainView>() {

    @Inject
    lateinit var adapter: ViewModelAdapter

    @Inject
    lateinit var stashImageService: StashImageService

    @Inject
    lateinit var imageFactory: ImageFactory

    fun onImageLongPress(id: String, uri: String?) {
        fetchImageMetadata(id, uri)
    }

    fun updateImages(images: List<ImageResult>) {
        val viewModels = images.map { imageFactory.createImageViewModel(it, ::onImageLongPress) }
        adapter.setViewModels(viewModels)
    }

    /*
    Normally would place this in a data repository class, kept here for simplicity
    */
    fun fetchSearch(searchPhrase: String, networkScheduler: Scheduler = RxSchedulers().network, mainScheduler: Scheduler = RxSchedulers().main) {
        view?.showProgess(true)
        disposables += stashImageService.searchImages(searchPhrase)
                .subscribeOn(networkScheduler)
                .observeOn(mainScheduler)
                .subscribe({ response ->
                    view?.showProgess(false)

                    val images = response.images
                    updateImages(images)
                }, {
                    view?.showError(it)
                })
    }

    /*
     Normally would place this in a data repository class, kept here for simplicity
     */
    fun fetchImageMetadata(id: String, uri: String?, networkScheduler: Scheduler = RxSchedulers().network, mainScheduler: Scheduler = RxSchedulers().main) {
        disposables += stashImageService.getImageMetadata(id)
                .subscribeOn(networkScheduler)
                .flatMap { metadata -> stashImageService.getSimilarImages(id).subscribeOn(networkScheduler).map { similarImages -> metadata to similarImages } }
                .observeOn(mainScheduler)
                .subscribe({
                    val metadata = it.first.metadata.firstOrNull()
                    val similarImages = ArrayList(it.second.images)

                    metadata?.let {
                        view?.showDialog(metadata, uri, similarImages)
                    }
                }, {
                    view?.showError(it)
                })

    }
}