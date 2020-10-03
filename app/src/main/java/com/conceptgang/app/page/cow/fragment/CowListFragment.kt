package com.conceptgang.app.page.cow.fragment

import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.*
import com.conceptgang.app.base.BaseEpoxyFragment
import com.conceptgang.app.model.Cow
import com.conceptgang.app.page.cow.viewmodel.CowListViewModel
import com.conceptgang.component.model.CowProfileViewData
import com.conceptgang.component.model.ViewCallback
import com.conceptgang.component.util.exhaustive

class CowListFragment : BaseEpoxyFragment() {

    val viewModel by fragmentViewModel(CowListViewModel::class)

    override val viewCallback: ViewCallback = { index, viewData, tag ->

        if(viewData is CowProfileViewData && tag is Cow){
            findNavController().navigate(CowListFragmentDirections.globalToCowDetailFragment(tag))
        }
    }


    override fun invalidate()  = withState(viewModel){state ->
        when(val views = state.views){
            is Uninitialized -> {
                viewModel.loadCow(0)
            }
            is Loading -> {
                setLoading(true)
            }
            is Success ->{
                setLoading(false)
                setEpoxyData(views())
            }
            is Fail -> {
                showError(views.error)
            }
        }.exhaustive
    }

}