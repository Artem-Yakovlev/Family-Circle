package com.tydeya.familycircle.ui.planpart.kitchenorganizer.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.FoodForBuyFragment
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.FoodInFridge

class KitchenFoodAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2;

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FoodForBuyFragment()
            1 -> FoodInFridge()
            else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
        }
    }
}