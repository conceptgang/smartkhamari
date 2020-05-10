package com.conceptgang.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.conceptgang.component.model.ViewData
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class TestFragmentAdapter(
    fa: FragmentActivity,
    private val data: ArrayList<Pair<String, Class<out Fragment>>>
) : FragmentStateAdapter(fa) {


    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment {

        return data[position].second.newInstance()

    }

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutList = ArrayList<Pair<String, Class<out Fragment>>>()
        layoutList.add(Pair("Raw Comp", RawComponentFragment::class.java))
        layoutList.add(Pair("First Page", FirstFragment::class.java))
        layoutList.add(Pair("Second Page", SecondFragment::class.java))
        layoutList.add(Pair("Third Page", ThirdFragment::class.java))

        viewPager2.adapter = TestFragmentAdapter(this, layoutList)
        TabLayoutMediator(tablayout, viewPager2) { tab, position ->
            tab.text = layoutList[position].first
        }.attach()
    }
}


class FirstFragment : BaseFragment(){
    override val viewData: List<ViewData> = listOf(


    )
}

class SecondFragment : BaseFragment(){
    override val viewData: List<ViewData> = listOf()
}

class ThirdFragment : BaseFragment(){
    override val viewData: List<ViewData> = listOf()
}

class RawComponentFragment : Fragment(R.layout.fragment_raw_component) {

}