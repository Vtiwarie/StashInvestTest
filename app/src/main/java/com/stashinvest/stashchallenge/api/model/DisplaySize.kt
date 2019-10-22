package com.stashinvest.stashchallenge.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DisplaySize(
        @SerializedName("is_watermarked")
        val isWatermarked: Boolean,
        val name: String,
        val uri: String): Parcelable
