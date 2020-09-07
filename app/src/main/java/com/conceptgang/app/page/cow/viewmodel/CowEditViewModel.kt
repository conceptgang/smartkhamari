package com.conceptgang.app.page.cow.viewmodel

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.conceptgang.app.MainActivity
import com.conceptgang.app.base.BaseViewModel
import com.conceptgang.app.data.model.FireStorePath
import com.conceptgang.app.data.remote.FirebaseAuthClient
import com.conceptgang.app.data.repository.KhamariRepository
import com.conceptgang.app.model.Cow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import java.lang.Exception

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

    fun createCow(cow: Cow){

        val user = FirebaseAuth.getInstance().currentUser!!

        val userCowStore = Firebase.firestore.collection(FireStorePath.user).document(user.uid).collection(FireStorePath.cow)

        viewModelScope.launch {
            try {
                val ref = userCowStore.document()
                val newCow = cow.copy(id = ref.id)
                ref.set(newCow).await()
                setState { copy(saved = Success(true)) }
            } catch (ex: Exception){
                setState { copy(saved = Fail(ex)) }
            }

        }
    }


}

