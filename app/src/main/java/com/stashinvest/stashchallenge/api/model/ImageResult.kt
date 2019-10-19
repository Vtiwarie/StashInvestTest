package com.stashinvest.stashchallenge.api.model

import com.google.gson.annotations.SerializedName

data class ImageResult(
        val id: String,
        val title: String,
        @SerializedName("display_sizes")
        val displaySizes: List<DisplaySize>) {
    
    val thumbUri: String?
        get() {
            for ((_, name, uri) in displaySizes) {
                if ("thumb" == name) {
                    return uri
                }
            }
            
            return null
        }
}
