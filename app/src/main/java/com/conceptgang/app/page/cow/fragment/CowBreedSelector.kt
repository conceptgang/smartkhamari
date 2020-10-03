package com.conceptgang.app.page.cow.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import com.conceptgang.app.databinding.BottomSheetCowBreedSelectorBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CowBreedSelector : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCowBreedSelectorBinding

    private val breedArray = arrayOf(
        "Black Bengal",
        "B",
        "c",
        "D",
        "e"
    )

    private var selectedBreed = breedArray[0]

    companion object {
        const val KEY = "AreaSelectionBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetCowBreedSelectorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.viewTreeObserver.addOnGlobalLayoutListener {
            val dialog = dialog as BottomSheetDialog?
            val bottomSheet: FrameLayout =
                (dialog as BottomSheetDialog).findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isHideable = false
            behavior.peekHeight = binding.root.height
        }

        val decorView = dialog?.window?.decorView
        val touchOutsideView = decorView?.findViewById<View>(com.google.android.material.R.id.touch_outside)
        touchOutsideView?.setOnClickListener(null)

        binding.progressBar.visibility = View.GONE
        binding.areaPicker.maxValue = breedArray.size - 1
        binding.areaPicker.minValue = 0
        binding.areaPicker.displayedValues = breedArray

        binding.areaPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            selectedBreed = breedArray[newVal]
            binding.selectedAreaTxt.text = selectedBreed
        }


        binding.cancelButton.setOnClickListener { dismiss() }
        binding.okButton.setOnClickListener {

            parentFragmentManager.setFragmentResult(
                KEY, // Same request key FragmentA used to register its listener
                bundleOf(KEY to selectedBreed) // The data to be passed to FragmentA
            )

            dismiss()

        }
    }
}