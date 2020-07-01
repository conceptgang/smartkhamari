package com.conceptgang.app

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.conceptgang.app.data.remote.FirebaseAuthClient
import com.conceptgang.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val firebaseAuthClient by lazy {  FirebaseAuthClient(this) }

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

    }
}