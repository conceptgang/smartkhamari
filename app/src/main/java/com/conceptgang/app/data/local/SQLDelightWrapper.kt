package com.conceptgang.app.data.local

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import com.squareup.sqldelight.ColumnAdapter
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


object DobColumnAdapter : ColumnAdapter<Calendar, String> {

    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    override fun decode(databaseValue: String): Calendar {
        val calender = Calendar.getInstance()
        calender.time = formatter.parse(databaseValue)!!
        return calender
    }

    override fun encode(value: Calendar): String {
        return formatter.format(value.time)
    }
}

object ImageColumnAdapter : ColumnAdapter<Bitmap, ByteArray> {
    override fun decode(databaseValue: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(databaseValue, 0, databaseValue.size)
    }

    override fun encode(value: Bitmap): ByteArray {
        val blob = ByteArrayOutputStream()
        value.compress(CompressFormat.PNG, 0 /* Ignored for PNGs */, blob)
        return blob.toByteArray()
    }
}