package com.conceptgang.component.model

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import java.security.InvalidParameterException
import java.util.*


typealias ViewCallback = (Int, ViewData, Any?)->Unit


sealed class ViewData {
    abstract val tag: Any
    abstract val epoxyData: EpoxyData
}

