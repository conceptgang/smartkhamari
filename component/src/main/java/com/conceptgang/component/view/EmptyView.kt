package com.conceptgang.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.setPadding
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.conceptgang.component.model.EmptyViewData
import com.conceptgang.component.util.px
import com.conceptgang.component.model.ViewCallback

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EmptyView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init(attr)
    }

    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        init(attr)
    }


    @ModelProp
    lateinit var viewData: EmptyViewData

    @ModelProp
    @JvmField
    var viewIndex: Int = -1

    @CallbackProp
    @JvmField
    @Nullable
    var viewCallback: ViewCallback? = null


    private fun init(attr: AttributeSet?) {

    }

    @AfterPropsSet
    fun setup() {
        setPadding(viewData.padding.px)
    }

}