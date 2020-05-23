package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.alllists.FoodForBuyFragment
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodinfridge.FoodInFridgeFragment

class KitchenOrganizerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FoodForBuyFragment()
            1 -> FoodInFridgeFragment()
            else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
        }
    }
}