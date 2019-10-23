package com.stashinvest.stashchallenge.api

import com.stashinvest.stashchallenge.api.model.ImageResponse
import com.stashinvest.stashchallenge.api.model.MetadataResponse
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StashImageService @Inject constructor(
        var api: StashImagesApi
) {

    fun searchImages(phrase: String): Single<ImageResponse> {
        return api.searchImages(phrase, FIELDS, SORT_ORDER)
    }

    fun getImageMetadata(id: String): Flowable<MetadataResponse> {
        return api.getImageMetadata(id)
    }

    fun getSimilarImages(id: String): Flowable<ImageResponse> {
        return api.getSimilarImages(id)
    }

    companion object {
        val FIELDS = "id,title,thumb"
        val SORT_ORDER = "best"

        data class RxSchedulers(
                val network: Scheduler = Schedulers.io(),
                val disk: Scheduler = Schedulers.single(),
                val main: Scheduler = AndroidSchedulers.mainThread()
        )
    }
}
