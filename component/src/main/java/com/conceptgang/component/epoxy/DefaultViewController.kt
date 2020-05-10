package com.conceptgang.component.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.conceptgang.component.model.*
import com.conceptgang.component.util.exhaustive
import com.conceptgang.component.view.*

class DefaultViewController(

    private val callback: ViewCallback

) : TypedEpoxyController<List<ViewData>>() {
    override fun buildModels(data: List<ViewData>) {

        data.forEachIndexed { index, viewData ->

            when(viewData){
                is HomeStateVerticalViewData -> homeStateVerticalView {
                    id("$index HomeStateVerticalViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is BioSprayViewData -> bioSprayView {
                    id("$index BioSprayViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is CostItemViewData -> costItemView {
                    id("$index CostItemViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is CowHealthListViewData -> cowHealthListView {
                    id("$index CowHealthListViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is CowProfileViewData -> cowProfileView {
                    id("$index CowProfileViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is DailySellItemViewData -> dailySellItemView {
                    id("$index DailySellItemViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is FoodListViewData -> foodListView {
                    id("$index FoodListViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is HomeStateHorizontalViewData -> homeStateHorizontalView {
                    id("$index HomeStateHorizontalViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is InvestorViewData -> investorView {
                    id("$index InvestorViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is OrderListViewData -> orderListView {
                    id("$index OrderListViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is ProductListViewData -> productListView {
                    id("$index ProductListViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is SearchViewData -> searchView {
                    id("$index SearchViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is StateSummeryViewData -> stateSummeryView {
                    id("$index StateSummeryViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
                is StockListViewData -> stockListView {
                    id("$index StockListViewData")
                    viewData(viewData)
                    viewCallback(callback)
                }
            }.exhaustive

        }


    }


}