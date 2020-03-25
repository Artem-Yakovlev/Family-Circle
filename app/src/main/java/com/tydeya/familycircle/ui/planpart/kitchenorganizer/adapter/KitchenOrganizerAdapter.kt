package com.tydeya.familycircle.ui.planpart.kitchenorganizer.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.cooking.CookingFragment
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.FoodForBuyFragment
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.FoodInFridgeFragment
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.history.FridgeHistory

class KitchenOrganizerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FoodForBuyFragment()
            1 -> FoodInFridgeFragment()
            2 -> CookingFragment()
            3 -> FridgeHistory()
            else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
        }
    }
}