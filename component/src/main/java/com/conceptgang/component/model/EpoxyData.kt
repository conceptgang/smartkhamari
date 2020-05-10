package com.conceptgang.component.model

import androidx.annotation.ColorRes

data class EpoxyData(
    val paddingLeftDp: Int,
    val paddingTopDp: Int,
    val paddingRightDp: Int,
    val paddingBottomDp: Int,
    @ColorRes val backgroundColor: Int = android.R.color.background_light,
    val hasDividerBottom:Boolean = false,
    val hasDividerTop:Boolean = false
) {

    constructor(
        horizontal: Int,
        vertical: Int,
        hasDividerBottom:Boolean = false,
        hasDividerTop:Boolean = false
    ): this(horizontal, vertical, horizontal, vertical,android.R.color.background_light, hasDividerBottom, hasDividerTop)

    constructor(
        padding: Int,
        hasDividerBottom:Boolean = false,
        hasDividerTop:Boolean = false
    ): this(padding, padding, padding, padding, android.R.color.background_light, hasDividerBottom, hasDividerTop)

    companion object {
        val SMALL = EpoxyData(16,0)
        val MEDIUM = EpoxyData(32, 0)
    }

}