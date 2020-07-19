package com.conceptgang.app.page.cow.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.conceptgang.app.base.BaseViewModel
import com.conceptgang.component.model.ViewData

data class CowListState(
    val views: Async<List<ViewData>> = Uninitialized
): MvRxState

class CowListViewModel(state: CowListState): BaseViewModel<CowListState>(state){

}