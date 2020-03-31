package com.tydeya.familycircle.ui.planpart.kitchenorganizer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tydeya.familycircle.R
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.adapter.KitchenOrganizerAdapter
import kotlinx.android.synthetic.main.fragment_kitchen_organizer.*


class KitchenOrganizerFragment : Fragment(R.layout.fragment_kitchen_organizer) {

    private lateinit var kitchenOrganizerAdapter: KitchenOrganizerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kitchenOrganizerAdapter = KitchenOrganizerAdapter(this)
        viewPager = kitchen_main_pager
        viewPager.adapter = kitchenOrganizerAdapter

        TabLayoutMediator(kitchen_main_tab_layout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.kitchen_organizer_shopping_list_title)
                1 -> getString(R.string.kitchen_organizer_food_in_fridge_title)
                else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
            }
        }.attach()

    }
}
