package com.stashinvest.stashchallenge.api

import com.stashinvest.stashchallenge.api.model.ImageResponse
import com.stashinvest.stashchallenge.api.model.MetadataResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StashImagesApi {
    @GET("search/images")
    fun searchImages(@Query("phrase") phrase: String,
                     @Query("fields") fields: String,
                     @Query("sort_order") sortOrder: String): Single<ImageResponse>

    @GET("images/{id}")
    fun getImageMetadata(@Path("id") id: String): Flowable<MetadataResponse>

    @GET("images/{id}/similar")
    fun getSimilarImages(@Path("id") id: String): Flowable<ImageResponse>
}
