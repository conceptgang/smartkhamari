package com.conceptgang.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.conceptgang.component.epoxy.DefaultItemDecoration
import com.conceptgang.component.epoxy.DefaultViewController
import com.conceptgang.component.model.*
import com.conceptgang.sample.databinding.FragmentBaseBinding

abstract class BaseFragment : Fragment(){

    private lateinit var binding: FragmentBaseBinding
    private val decoration: DefaultItemDecoration = DefaultItemDecoration()
    private val controller: DefaultViewController by lazy { DefaultViewController(viewCallback) }

    protected open val viewCallback: ViewCallback = { i, viewData, any -> }
    abstract val viewData: List<ViewData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.epoxyRecycler.addItemDecoration(decoration)
        binding.epoxyRecycler.setController(controller)

        decoration.views = viewData
        controller.setData(viewData)
    }

}