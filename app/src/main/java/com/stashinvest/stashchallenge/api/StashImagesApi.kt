package com.stashinvest.stashchallenge.api

import com.stashinvest.stashchallenge.api.model.ImageResponse
import com.stashinvest.stashchallenge.api.model.MetadataResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StashImagesApi {
    @GET("search/images")
    fun searchImages(@Query("phrase") phrase: String,
                     @Query("fields") fields: String,
                     @Query("sort_order") sortOrder: String): Call<ImageResponse>

    @GET("images/{id}")
    fun getImageMetadata(@Path("id") id: String): Call<MetadataResponse>

    @GET("images/{id}/similar")
    fun getSimilarImages(@Path("id") id: String): Call<ImageResponse>
}
