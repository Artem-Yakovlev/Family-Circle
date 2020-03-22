package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.BuyCatalogsRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.OnBuyCatalogClickListener
import kotlinx.android.synthetic.main.fragment_food_for_buy.*
import java.util.*
import kotlin.collections.ArrayList

class FoodForBuyFragment : Fragment(R.layout.fragment_food_for_buy), OnBuyCatalogClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buyLists = ArrayList<BuyCatalog>()
        buyLists.add(BuyCatalog("Birthday", Date(), ArrayList()))
        buyLists.add(BuyCatalog("Week", Date(), ArrayList()))
        buyLists.add(BuyCatalog("Country", Date(), ArrayList()))

        val adapter = BuyCatalogsRecyclerViewAdapter(context!!, buyLists, this)
        food_for_buy_recyclerview.adapter = adapter

        food_for_buy_recyclerview.layoutManager = LinearLayoutManager(context!!,
                LinearLayoutManager.VERTICAL, false)

        buy_list_floating_button.attachToRecyclerView(food_for_buy_recyclerview)
    }

    override fun onClick(position: Int) {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.buyListFragment)
    }
}
