package com.example.earsproject.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClassDataModel(
    val latitude: Double,
    val longitude: Double,
): Parcelable