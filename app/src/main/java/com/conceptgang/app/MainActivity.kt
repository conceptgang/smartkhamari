package com.conceptgang.app

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.conceptgang.app.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        //window?.statusBarColor = getThemeColor(R.attr.colorSurface)
        //window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//  set status text dark
        window.statusBarColor = ContextCompat.getColor(this, R.color.surface);// set status background white

        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onResume() {
        super.onResume()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            binding.collapseView.visibility = View.GONE
            binding.searchbar.visibility = View.GONE
            binding.bottomNav.visibility = View.GONE

            when(destination.id){
                R.id.homeFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }

                R.id.cowListFragment -> {
                    binding.collapseView.visibility = View.VISIBLE
                    binding.searchbar.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }

        binding.bottomNav.setOnNavigationItemSelectedListener { item ->

            when(item.itemId){
                R.id.bottomNavHome -> {

                }

                R.id.bottomNavCow -> {
                    navController.navigate(R.id.cowListFragment)
                }
            }

            return@setOnNavigationItemSelectedListener true
        }

    }
}