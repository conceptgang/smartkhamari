package com.conceptgang.app.model

import android.graphics.Bitmap
import com.google.firebase.Timestamp

data class Cow(
    val id: String,
    val image: String,
    val name: String,
    val breed: String,
    val dob: Timestamp,
    val isFromOutside: Boolean,
    val buyingPrice: Double = 0.0,
    val isOpenForCell: Boolean,
    val sellingPrice: Double = 0.0,
    val isPregnant: Boolean
)