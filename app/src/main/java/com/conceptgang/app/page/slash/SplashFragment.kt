package com.conceptgang.app.page.slash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.conceptgang.app.MainActivity
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding
    private val firebaseAuthClient = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            delay(1000)

            try {
                if(firebaseAuthClient.currentUser != null){
                    findNavController().navigate(SplashFragmentDirections.globalToHomeFragment())
                } else{
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
                }
            } catch (ex: Exception) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
            }
        }
    }

    override fun invalidate() {}

}
