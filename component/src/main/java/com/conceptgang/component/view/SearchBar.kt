package com.conceptgang.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.conceptgang.component.databinding.WidgetSearchBarBinding
import com.conceptgang.component.util.px
import com.google.android.material.card.MaterialCardView


class SearchBar: MaterialCardView, CoordinatorLayout.AttachedBehavior{

    private lateinit var binding: WidgetSearchBarBinding

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

    private fun init(attr: AttributeSet?) {

        val inflater = LayoutInflater.from(context)
        binding = WidgetSearchBarBinding.inflate(inflater, this, true)

        this.cardElevation = 16.px.toFloat()
        this.radius = 16.px.toFloat()

    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return SearchBarBehavior()
    }
}