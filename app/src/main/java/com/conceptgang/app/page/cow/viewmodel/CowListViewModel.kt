package com.conceptgang.app.page.cow.viewmodel

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.conceptgang.app.base.BaseViewModel
import com.conceptgang.app.data.model.FireStorePath
import com.conceptgang.app.model.Cow
import com.conceptgang.component.model.CowProfileViewData
import com.conceptgang.component.model.EmptyViewData
import com.conceptgang.component.model.ViewData
import com.conceptgang.component.model.ViewString
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

data class CowListState(
    val views: Async<List<ViewData>> = Uninitialized
): MvRxState

class CowListViewModel(state: CowListState): BaseViewModel<CowListState>(state){

    var cowList: Set<Cow> = mutableSetOf()

    fun loadCow(page: Int){

        setState { copy(views = Loading()) }

        viewModelScope.launch {
            try {
                val cowList = FireStorePath
                    .userCowStore
                    .get()
                    .await()
                    .map { it.toObject(Cow::class.java) }

                val views: MutableList<ViewData> = cowList.map {
                    CowProfileViewData(
                        cowName = ViewString(it.name),
                        cowId = ViewString("CID${it.id.take(10)}"),
                        breed = ViewString(it.breed),
                        tag = it
                    )
                }.toMutableList()

                views.add(0, EmptyViewData(30))
                views.add(EmptyViewData(50))

                setState { copy(views = Success(views)) }
            } catch (ex: Exception){
                Timber.e(ex)
                setState { copy(views = Fail(ex)) }
            }

        }
    }

}