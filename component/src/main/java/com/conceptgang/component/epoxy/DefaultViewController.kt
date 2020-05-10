package com.conceptgang.component.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.conceptgang.component.model.ViewCallback
import com.conceptgang.component.model.ViewData
import com.conceptgang.component.util.exhaustive

class DefaultViewController(

    private val callback: ViewCallback

) : TypedEpoxyController<List<ViewData>>() {
    override fun buildModels(data: List<ViewData>) {

        data.forEachIndexed { index, viewData ->

//            when(viewData){
//
//
//
//            }.exhaustive

        }


    }


}