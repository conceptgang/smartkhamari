package com.conceptgang.app.page.onboarding.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.databinding.FragmentSignupBinding

class SignUpFragment : BaseFragment(){

    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun invalidate() {

    }

}