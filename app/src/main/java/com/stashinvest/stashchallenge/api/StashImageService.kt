package com.stashinvest.stashchallenge.api

import com.stashinvest.stashchallenge.api.model.ImageResponse
import com.stashinvest.stashchallenge.api.model.MetadataResponse
import retrofit2.Call
import javax.inject.Inject

class StashImageService @Inject constructor() {
    
    @Inject
    lateinit var api: StashImagesApi
    
    fun searchImages(phrase: String): Call<ImageResponse> {
        return api.searchImages(phrase, FIELDS, SORT_ORDER)
    }
    
    fun getImageMetadata(id: String): Call<MetadataResponse> {
        return api.getImageMetadata(id)
    }
    
    fun getSimilarImages(id: String): Call<ImageResponse> {
        return api.getSimilarImages(id)
    }
    
    companion object {
        val FIELDS = "id,title,thumb"
        val SORT_ORDER = "best"
    }
}
