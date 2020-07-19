package com.conceptgang.app.page.cow.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentResultListener
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.data.local.DobColumnAdapter
import com.conceptgang.app.page.cow.viewmodel.CowEditViewModel
import com.conceptgang.component.databinding.FragmentCowEditBinding
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.google.android.material.datepicker.MaterialDatePicker
import timber.log.Timber
import java.util.*


class CowEditFragment : BaseFragment() {

    private val viewModel by fragmentViewModel(CowEditViewModel::class)
    private lateinit var binding: FragmentCowEditBinding

    val imageRequestCode = 100
    var options = Options.init()
        .setRequestCode(imageRequestCode) //Request code for activity results
        .setCount(1) //Number of images to restict selection count
        .setFrontfacing(false) //Front Facing camera on start
        .setExcludeVideos(true) //Option to exclude videos
        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT) //Orientaion

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCowEditBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dobTxt.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener { data ->

                val calendar = Calendar.getInstance(Locale.ENGLISH)
                calendar.timeInMillis = data

                binding.dobTxt.text = DobColumnAdapter.encode(calendar)
            }

            datePicker.show(childFragmentManager, "Hello")
        }

        binding.breedTxt.setOnClickListener {
            val breedSelector = CowBreedSelector()

            breedSelector.show(childFragmentManager, "BREED")

            childFragmentManager.setFragmentResultListener(
                CowBreedSelector.KEY,
                viewLifecycleOwner,
                FragmentResultListener { requestKey, result ->

                    val selectedBreed = result.getString(CowBreedSelector.KEY, "Local")

                    binding.breedTxt.text = selectedBreed

                    Timber.d("Area: $selectedBreed")
                }
            )
        }

        binding.cowImage.setOnClickListener {
            Pix.start(this, options);
        }
    }

    override fun invalidate() = withState(viewModel) { state ->

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            val returnValue: ArrayList<String> = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)

            val path = returnValue[0]
            val bitmap = BitmapFactory.decodeFile(path)

            binding.cowImage.setImageBitmap(bitmap)
            binding.cowImage.scaleType = ImageView.ScaleType.CENTER_CROP
            Timber.tag("CowEditFragment").d("${data.data}")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(this, options)
                } else {

                }
                return
            }
        }
    }
}