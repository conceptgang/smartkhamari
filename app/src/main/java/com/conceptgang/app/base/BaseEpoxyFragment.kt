package com.conceptgang.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView
import com.conceptgang.app.R
import com.conceptgang.app.base.BaseFragment
import com.conceptgang.app.databinding.FragmentBaseEpoxyBinding
import com.conceptgang.component.epoxy.DefaultItemDecoration
import com.conceptgang.component.epoxy.DefaultViewController
import com.conceptgang.component.model.ViewCallback
import com.conceptgang.component.model.ViewData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.android.synthetic.main.fragment_base_epoxy.view.*

abstract class BaseEpoxyFragment : BaseFragment() {

    protected open var itemDecoration: DefaultItemDecoration = DefaultItemDecoration()
    protected abstract val viewCallback: ViewCallback


    private val viewController: DefaultViewController by lazy {
        DefaultViewController(
            viewCallback
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val baseBinding = FragmentBaseEpoxyBinding.inflate(inflater, container, false)

        return baseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler)

        epoxyRecyclerView.setController(viewController)
        epoxyRecyclerView.addItemDecoration(itemDecoration)
    }


    protected fun setEpoxyData(data: List<ViewData>) {
        itemDecoration.views = data
        viewController.setData(data)
    }

}
