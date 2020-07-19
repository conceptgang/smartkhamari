package com.conceptgang.app.page.cow.viewmodel

import com.airbnb.mvrx.*
import com.conceptgang.app.MainActivity
import com.conceptgang.app.base.BaseViewModel
import com.conceptgang.app.data.repository.KhamariRepository

data class CowEditState(
    val saved: Async<Boolean> = Uninitialized
): MvRxState

class CowEditViewModel(state: CowEditState, val repository: KhamariRepository): BaseViewModel<CowEditState>(state){

    companion object: MvRxViewModelFactory<CowEditViewModel, CowEditState>{

        override fun create(
            viewModelContext: ViewModelContext,
            state: CowEditState
        ): CowEditViewModel? {

            val activity = viewModelContext.activity<MainActivity>()

            return CowEditViewModel(state, activity.khamariRepository)
        }
    }


}

