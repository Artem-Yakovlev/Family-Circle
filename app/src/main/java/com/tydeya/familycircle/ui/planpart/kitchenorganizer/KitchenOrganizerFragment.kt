package com.tydeya.familycircle.ui.planpart.kitchenorganizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tydeya.familycircle.R
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.adapter.KitchenFoodAdapter
import kotlinx.android.synthetic.main.fragment_kitchen_organizer.*


class KitchenOrganizerFragment : Fragment() {

    private lateinit var kitchenFoodAdapter: KitchenFoodAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_kitchen_organizer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kitchenFoodAdapter = KitchenFoodAdapter(this)
        viewPager = kitchen_main_pager
        viewPager.adapter = kitchenFoodAdapter

        TabLayoutMediator(kitchen_main_tab_layout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.kitchen_organizer_shopping_list_title)
                1 -> getString(R.string.kitchen_organizer_food_in_fridge)
                else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
            }
        }.attach()

    }
}
