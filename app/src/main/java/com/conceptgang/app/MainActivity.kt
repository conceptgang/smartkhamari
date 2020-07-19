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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.conceptgang.app.data.local.DobColumnAdapter
import com.conceptgang.app.data.local.ImageColumnAdapter
import com.conceptgang.app.data.remote.FirebaseAuthClient
import com.conceptgang.app.data.repository.KhamariRepository
import com.conceptgang.app.data.sqldelight.Cow
import com.conceptgang.app.databinding.ActivityMainBinding
import com.fxn.pix.Pix
import com.squareup.sqldelight.android.AndroidSqliteDriver
import timber.log.Timber
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    val firebaseAuthClient by lazy {  FirebaseAuthClient(this) }
    val khamariRepository by lazy {
        val sqlDriver = AndroidSqliteDriver(Database.Schema, this, "khamari.db")
        val database = Database(
            driver = sqlDriver,
            cowAdapter = Cow.Adapter(
                imageAdapter = ImageColumnAdapter,
                dobAdapter = DobColumnAdapter
            )
        )

        return@lazy KhamariRepository(
            firebaseAuthClient = firebaseAuthClient,
            database = database
        )
    }

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

            binding.appbar.visibility = View.GONE
            binding.searchbar.visibility = View.GONE
            binding.bottomNav.visibility = View.GONE

            when(destination.id){

                R.id.homeFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }

                R.id.cowListFragment -> {

                    binding.appbar.visibility = View.VISIBLE
                    binding.searchbar.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.VISIBLE
                }

                R.id.cowEditFragment -> {

                    binding.bottomNav.visibility = View.VISIBLE
                }
            }


        }

    }
}