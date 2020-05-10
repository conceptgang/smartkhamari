package com.conceptgang.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Nullable
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.conceptgang.component.databinding.WidgetInvestorBinding
import com.conceptgang.component.model.InvestorViewData
import com.conceptgang.component.util.px
import com.conceptgang.component.model.ViewCallback
import com.google.android.material.card.MaterialCardView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class InvestorView : MaterialCardView {

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
    lateinit var viewData: InvestorViewData

    @ModelProp
    @JvmField
    var viewIndex: Int = -1

    @CallbackProp
    @JvmField
    @Nullable
    var viewCallback: ViewCallback? = null


    private lateinit var binding: WidgetInvestorBinding

    private fun init(attr: AttributeSet?) {
        val inflater = LayoutInflater.from(context)
        binding = WidgetInvestorBinding.inflate(inflater, this, true)

        cardElevation = 3.px.toFloat()
        radius = 10.px.toFloat()


    }

    @AfterPropsSet
    fun setup() {


    }

}