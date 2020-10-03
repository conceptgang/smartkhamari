package com.conceptgang.app.page.onboarding.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.*
import com.conceptgang.app.R
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.databinding.FragmentSignupBinding
import com.conceptgang.app.page.onboarding.viewmodel.OnBoardingViewModel
import com.conceptgang.component.dialog.ZHDialog
import com.conceptgang.component.dialog.ZHDialogType
import com.conceptgang.component.util.isValidPhoneNumber
import com.conceptgang.component.util.isValidPhoneNumberWithCode
import timber.log.Timber

class SignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentSignupBinding
    private val onBoardingViewModel by activityViewModel(OnBoardingViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBtn.setOnClickListener {

            var isValid = true

            val fullName = binding.fullNameEditText.text.toString()
            if (fullName.isEmpty()) {
                isValid = false
                binding.fullNameEditTextLayout.error = getString(R.string.name_cant_empty)
            }

            val khamariName = binding.khamarNameEditText.text.toString()

            var phoneNumber = binding.phoneEditText.text.toString()
            if (phoneNumber.isValidPhoneNumber) phoneNumber = "+88$phoneNumber"
            if (!phoneNumber.isValidPhoneNumberWithCode) {
                isValid = false
                binding.phoneEditTextLayout.error = getString(R.string.inalid_phone_number)
            }

            if (isValid) {
                onBoardingViewModel.fullName = fullName
                onBoardingViewModel.khamariName = khamariName
                onBoardingViewModel.sendOtp(phoneNumber)
            }
        }

        binding.signInBtn.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }
    }

    override fun invalidate() = withState(onBoardingViewModel) { state ->

        when (val sendOtpResult = state.otpSent) {


            is Loading -> {
                binding.progressBarInclude.progressBarInclude.visibility = View.VISIBLE
            }

            is Fail -> {
                Timber.e(sendOtpResult.error)
                binding.progressBarInclude.progressBarInclude.visibility = View.GONE
                val dialog = ZHDialog(
                    R.drawable.question,
                    getString(R.string.oops),
                    getString(R.string.something_went_wrong),
                    ZHDialogType.YesDialog(
                        onYes = { },
                        onClick = { }
                    ),
                    positiveTitle = "Try Again"
                )

                dialog.show(childFragmentManager, "SHOW")
            }

            is Success -> {
                Timber.d("Success Clicked ")
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToOtpFragment())
            }
        }
    }

}