package com.stashinvest.stashchallenge.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageResult(
        val id: String,
        val title: String,
        @SerializedName("display_sizes")
        val displaySizes: List<DisplaySize>) : Parcelable {

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
