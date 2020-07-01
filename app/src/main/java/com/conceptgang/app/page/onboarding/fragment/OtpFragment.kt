package com.conceptgang.app.page.onboarding.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.mvrx.*
import com.conceptgang.app.R
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.databinding.FragmentOtpBinding
import com.conceptgang.app.databinding.FragmentSignupBinding
import com.conceptgang.app.page.onboarding.viewmodel.OnBoardingViewModel
import com.conceptgang.component.util.hideKeyboard
import com.conceptgang.component.util.showKeyboard
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.poovam.pinedittextfield.PinField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class OtpFragment : BaseFragment(){

    private lateinit var binding: FragmentOtpBinding
    private val onBoardingViewModel by activityViewModel(OnBoardingViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBoardingViewModel.clearState()

        val phoneNumber = onBoardingViewModel.mobileNumber
        val subtitle = getString(R.string.otp_subtitle, phoneNumber)

        val spannableSubtitle = SpannableString(subtitle)

        spannableSubtitle[23, 23 + phoneNumber.length] = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.green))
        spannableSubtitle[subtitle.length - 6, subtitle.length] = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.orange))
        spannableSubtitle[subtitle.length - 6, subtitle.length] = UnderlineSpan()

        binding.signUpSubTitle.text = spannableSubtitle

        lifecycleScope.launch {
            delay(500)
            showKeyboard(binding.otpView)
            delay(5000)
            binding.resendOtpTxt.visibility = View.VISIBLE
        }

        binding.signUpSubTitle.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.resendOtpTxt.setOnClickListener {
            onBoardingViewModel.sendOtp(onBoardingViewModel.mobileNumber)
        }

        binding.otpView.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {
                onBoardingViewModel.loginWithOtp(enteredText)
                return false
            }
        }
    }

    override fun invalidate() = withState(onBoardingViewModel) { state ->

        when(val user = state.user){

            is Success -> {
                Timber.d("Success")
                hideKeyboard()
                findNavController().navigate(OtpFragmentDirections.globalToHomeFragment())
            }

            is Loading -> {
                binding.progressBarInclude.progressBarInclude.visibility = View.VISIBLE
            }

            is Fail -> {
                Timber.d(user.error)

                binding.progressBarInclude.progressBarInclude.visibility = View.GONE
                if(user.error is FirebaseAuthInvalidCredentialsException){
                    binding.wrongOtpTxt.visibility = View.VISIBLE
                }

            }
        }

    }

}