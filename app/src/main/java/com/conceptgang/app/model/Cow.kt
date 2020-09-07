package com.conceptgang.app.model

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cow(
    val id: String = "",
    val image: String = "",
    val name: String = "",
    val breed: String = "",
    val dob: Timestamp = Timestamp.now(),
    val isFromOutside: Boolean = false,
    val buyingPrice: Double = 0.0,
    val isOpenForCell: Boolean = false,
    val sellingPrice: Double = 0.0,
    val isPregnant: Boolean = false
) : Parcelable