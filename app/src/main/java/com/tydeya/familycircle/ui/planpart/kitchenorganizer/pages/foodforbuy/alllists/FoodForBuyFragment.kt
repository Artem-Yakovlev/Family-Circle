package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyList
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.BuyListsRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.OnBuyListClickListener
import kotlinx.android.synthetic.main.fragment_food_for_buy.*
import java.util.*
import kotlin.collections.ArrayList

class FoodForBuyFragment : Fragment(R.layout.fragment_food_for_buy), OnBuyListClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buyLists = ArrayList<BuyList>()
        buyLists.add(BuyList("Birthday", Date(), ArrayList()))
        buyLists.add(BuyList("Week", Date(), ArrayList()))
        buyLists.add(BuyList("Country", Date(), ArrayList()))

        val adapter = BuyListsRecyclerViewAdapter(context!!, buyLists, this)
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
