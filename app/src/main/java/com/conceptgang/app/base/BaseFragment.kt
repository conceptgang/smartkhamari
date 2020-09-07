package com.conceptgang.app.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.BaseMvRxFragment
import com.conceptgang.app.MainActivity


abstract class BaseFragment : BaseMvRxFragment() {

    val mainActivity: MainActivity by lazy { requireActivity() as MainActivity }

    fun setLoading(isLoading: Boolean){
        mainActivity.binding.loadingView.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    fun showError(error: Throwable){

    }
}