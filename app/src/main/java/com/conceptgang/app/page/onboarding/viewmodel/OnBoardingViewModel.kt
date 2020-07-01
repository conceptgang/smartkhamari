package com.conceptgang.app.page.onboarding.viewmodel

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.conceptgang.app.MainActivity
import com.conceptgang.app.base.BaseViewModel
import com.conceptgang.app.data.model.SendOtpResult
import com.conceptgang.app.data.remote.FirebaseAuthClient
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.lang.Exception

data class OnBoardingState(
    val otpSent: Async<SendOtpResult> = Uninitialized,
    val user: Async<FirebaseUser> = Uninitialized
): MvRxState

class OnBoardingViewModel(state: OnBoardingState, val authClient: FirebaseAuthClient): BaseViewModel<OnBoardingState>(state) {

    lateinit var mobileNumber: String
    lateinit var sendOtpResult: SendOtpResult

    var fullName: String? = null
    var khamariName: String? = null


    companion object: MvRxViewModelFactory<OnBoardingViewModel, OnBoardingState>{

        override fun create(
            viewModelContext: ViewModelContext,
            state: OnBoardingState
        ): OnBoardingViewModel? {

            val mainActivity = viewModelContext.activity as MainActivity
            return OnBoardingViewModel(state, mainActivity.firebaseAuthClient)
        }
    }

    fun loginWithOtp(otpTxt: String){

        setState { copy(user = Loading()) }

        viewModelScope.launch {

            try {

                val user = authClient.signInWithOTP(sendOtpResult, otpTxt)
                setState { copy(user = Success(user)) }

            }catch (ex: Exception){
                setState { copy(user = Fail(ex)) }
            }

        }


    }

    fun clearState(){
        setState { copy(otpSent = Uninitialized, user = Uninitialized) }
    }

    fun sendOtp(phoneNumber: String){

        mobileNumber = phoneNumber
        setState { copy(otpSent = Loading()) }

        viewModelScope.launch {
            try {
                sendOtpResult = authClient.sendOTPCode(mobileNumber)
                setState { copy(otpSent = Success(sendOtpResult))}
            }catch (ex: Exception){
                setState { copy(otpSent = Fail(ex)) }
            }
        }
    }

}