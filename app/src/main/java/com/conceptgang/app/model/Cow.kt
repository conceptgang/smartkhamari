package com.conceptgang.app.model

import android.graphics.Bitmap
import java.util.*

data class Cow(
    val id: Long,
    val image: Bitmap,
    val name: String,
    val breed: String,
    val dob: Calendar,
    val isFromOutside: Boolean,
    val buyingPrice: Double = 0.0,
    val isOpenForCell: Boolean,
    val sellingPrice: Double = 0.0,
    val isPregnant: Boolean
)