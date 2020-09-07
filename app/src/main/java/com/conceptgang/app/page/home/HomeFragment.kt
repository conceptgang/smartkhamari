package com.conceptgang.app.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.databinding.FragmentHomeBinding
import com.conceptgang.app.databinding.FragmentSigninBinding

class HomeFragment : BaseFragment(){

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newCowBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.globalToCowEditFragment(null))
        }
    }

    override fun invalidate() {

    }

}
