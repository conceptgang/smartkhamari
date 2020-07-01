package com.conceptgang.app.page.onboarding.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.*
import com.conceptgang.app.R
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.databinding.FragmentSigninBinding
import com.conceptgang.app.page.onboarding.viewmodel.OnBoardingViewModel
import com.conceptgang.component.util.isValidPhoneNumber
import com.conceptgang.component.util.isValidPhoneNumberWithCode
import timber.log.Timber

class SignInFragment : BaseFragment(){

    private lateinit var binding: FragmentSigninBinding

    private val onBoardingViewModel by activityViewModel(OnBoardingViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        binding.signInBtn.setOnClickListener {
            var phoneNumber = binding.phoneEditText.text.toString()
            if(phoneNumber.isValidPhoneNumber) phoneNumber = "+88$phoneNumber"
            if(!phoneNumber.isValidPhoneNumberWithCode){
                binding.phoneEditTextLayout.error = getString(R.string.inalid_phone_number)
            }else{
                onBoardingViewModel.sendOtp(phoneNumber)
            }

            Timber.d(phoneNumber)
        }
    }

    override fun invalidate() = withState(onBoardingViewModel) { state ->

        when(val sendOtpResult = state.otpSent){


            is Loading -> {
                binding.progressBarInclude.progressBarInclude.visibility = View.VISIBLE
            }

            is Fail -> {
                Timber.d(sendOtpResult.error)
                binding.progressBarInclude.progressBarInclude.visibility = View.GONE
            }

            is Success -> {
                Timber.d("Success Clicked ")
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToOtpFragment())
            }
        }
    }

}