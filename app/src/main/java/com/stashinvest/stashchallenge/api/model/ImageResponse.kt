package com.stashinvest.stashchallenge.api.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
        @SerializedName("result_count")
        val resultCount: Int,
        val images: List<ImageResult>)
