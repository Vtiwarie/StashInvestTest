package com.stashinvest.stashchallenge.api.model

import com.google.gson.annotations.SerializedName

data class Image(
        val id: String,
        val artist: String,
        val caption: String,
        @SerializedName("license_model")
        val licenseModel: String,
        val title: String)
