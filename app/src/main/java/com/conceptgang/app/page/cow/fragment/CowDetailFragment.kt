package com.conceptgang.app.page.cow.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.data.local.DateColumnAdapter
import com.conceptgang.component.databinding.FragmentCowDetailBinding

class CowDetailFragment : BaseFragment(){

    private lateinit var binding: FragmentCowDetailBinding
    private val args by navArgs<CowDetailFragmentArgs>()
    private val cow by lazy { args.cow }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCowDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cowName.text = cow.name
        binding.cowID.text = cow.id.take(10)
        binding.breedText.text = cow.breed
        binding.dobText.text = DateColumnAdapter.encode(cow.dob.toDate())
        binding.placeofBirthText.text = if(cow.isFromOutside) "Outside" else "Firm"
        binding.priceText.text = "${cow.sellingPrice}"

        binding.editButton.setOnClickListener {
            findNavController().navigate(CowDetailFragmentDirections.globalToCowEditFragment(cow))
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun invalidate() {

    }
}