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
import com.conceptgang.app.R
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.databinding.FragmentOtpBinding
import com.conceptgang.app.databinding.FragmentSignupBinding
import com.conceptgang.component.util.showKeyboard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpFragment : BaseFragment(){

    private lateinit var binding: FragmentOtpBinding

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

        val phoneNumber = "3434234234234"
        val subtitle = getString(R.string.otp_subtitle, phoneNumber)

        val spannableSubtitle = SpannableString(subtitle)

        spannableSubtitle[23, 23 + phoneNumber.length] = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.green))
        spannableSubtitle[subtitle.length - 6, subtitle.length] = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.orange))
        spannableSubtitle[subtitle.length - 6, subtitle.length] = UnderlineSpan()

        binding.signUpSubTitle.text = spannableSubtitle

        lifecycleScope.launch {
            delay(500)
            showKeyboard(binding.otpView)
        }




    }

    override fun invalidate() {

    }

}