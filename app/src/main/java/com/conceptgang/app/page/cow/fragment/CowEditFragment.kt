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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.mvrx.*
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.data.local.DateColumnAdapter
import com.conceptgang.app.data.local.DobColumnAdapter
import com.conceptgang.app.data.model.FireStorePath
import com.conceptgang.app.model.Cow
import com.conceptgang.app.page.cow.viewmodel.CowEditViewModel
import com.conceptgang.component.databinding.FragmentCowEditBinding
import com.conceptgang.component.util.exhaustive
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import timber.log.Timber
import java.util.*


class CowEditFragment : BaseFragment() {

    private val viewModel by fragmentViewModel(CowEditViewModel::class)
    private lateinit var binding: FragmentCowEditBinding

    private val args by navArgs<CowEditFragmentArgs>()
    private val cow by lazy { args.cow }

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

        val givenCow = cow
        if (givenCow != null){
            binding.nameEditText.setText(givenCow.name)
            binding.breedTxt.setText(givenCow.breed)
            binding.dobTxt.setText(DateColumnAdapter.encode(givenCow.dob.toDate()))
            binding.buyingPriceEditTxt.setText("${givenCow.buyingPrice}")
            binding.outSideRadioButton.isChecked = givenCow.isFromOutside
            binding.firmRadioButton.isChecked = !givenCow.isFromOutside
            binding.sellingStatusSwitch.isChecked = givenCow.isOpenForCell
            binding.sellingPriceEditTxt.setText("${givenCow.sellingPrice}")
            binding.pregnancySwitch.isChecked = givenCow.isPregnant
        }

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

        binding.navigationIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {

            val name = binding.nameEditText.text.toString()
            val breed = binding.breedTxt.text.toString()
            val dob = binding.dobTxt.text.toString()
            val buyingPrice = binding.buyingPriceEditTxt.text.toString().toDouble()
            val isFromOutside = binding.outSideRadioButton.isChecked
            val isOpenForSelling = binding.sellingStatusSwitch.isChecked
            val sellingPrice = binding.sellingPriceEditTxt.text.toString().toDouble()
            val isPregnant = binding.pregnancySwitch.isChecked

            val cow = Cow(
                name = name,
                breed = breed,
                dob = Timestamp(DobColumnAdapter.decode(dob).time),
                buyingPrice = buyingPrice,
                isFromOutside = isFromOutside,
                isOpenForCell = isOpenForSelling,
                sellingPrice =  sellingPrice,
                isPregnant = isPregnant,
                id = FireStorePath.defaultCowID,
                image = FireStorePath.defaultCowImage
            )

            viewModel.createCow(cow)
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        when(val isSaved = state.saved){
            is Uninitialized -> {

            }
            is Loading -> {
                setLoading(true)
            }
            is Success -> {
                setLoading(false)
                viewModel.clearState()
                findNavController().navigate(CowEditFragmentDirections.actionCowEditFragmentToCowDetailFragment(isSaved()))
            }
            is Fail -> {
                showError(isSaved.error)
            }
        }.exhaustive
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