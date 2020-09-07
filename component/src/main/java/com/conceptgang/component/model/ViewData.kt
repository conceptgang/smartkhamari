package com.conceptgang.component.model

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.conceptgang.component.util.px
import java.security.InvalidParameterException
import java.util.*


typealias ViewCallback = (Int, ViewData, Any?)->Unit


sealed class ViewData {
    abstract val tag: Any?
    abstract val epoxyData: EpoxyData
}

data class EmptyViewData(
    val padding: Int,
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData(horizontal = 0, vertical = 0.px)
) : ViewData()

data class HomeStateVerticalViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class BioSprayViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class CostItemViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class CowHealthListViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class CowProfileViewData(
    val cowName: ViewString,
    val cowId: ViewString,
    val breed: ViewString,
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class DailySellItemViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class FoodListViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class HomeStateHorizontalViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class InvestorViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class OrderListViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class ProductListViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class SearchViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class StateSummeryViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()

data class StockListViewData(
    override val tag: Any? = null,
    override val epoxyData: EpoxyData = EpoxyData.SMALL
) : ViewData()


