package com.conceptgang.component.model

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.text.set
import java.security.InvalidParameterException

data class ViewStringStyle (
    @StyleRes val textAppearanceRes: Int,
    @ColorRes val colorRes: Int,
    val start: Int = 0,
    val end: Int = Int.MAX_VALUE
)

class ViewString private constructor(
    private val data: String? = null,
    @StringRes private val dataInt: Int = -1
){

    constructor(data: String): this(data, -1)
    constructor(@StringRes dataInt: Int): this(null, dataInt)

    init {
        if(data == null && dataInt == -1) throw InvalidParameterException("You must have to pass either string or string resource not both")
        else if (data != null && dataInt != -1) throw InvalidParameterException("You must have to pass either string or string resource not both")
    }

    private var styleData: ArrayList<ViewStringStyle> = ArrayList()

    fun addStringStyle(style: ViewStringStyle){
        styleData.add(style)
    }

    fun getValue(context: Context): SpannableString {

        val value =  data ?: context.getString(dataInt)

        val spannableString = SpannableString(value)

        styleData.forEach {style ->

            val end = if(style.end > value.length) value.length else style.end

            spannableString[style.start, end] = TextAppearanceSpan(context, style.textAppearanceRes)
            spannableString[style.start, end] = ForegroundColorSpan(ContextCompat.getColor(context, style.colorRes))

        }

        return spannableString

    }

}

fun String.toViewString() = ViewString(this)
fun @receiver:StringRes Int.toViewString() = ViewString(this)
