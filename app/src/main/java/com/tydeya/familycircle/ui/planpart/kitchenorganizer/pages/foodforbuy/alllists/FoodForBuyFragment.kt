package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyList
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.BuyListRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_food_for_buy.*
import java.util.*
import kotlin.collections.ArrayList

class FoodForBuyFragment : Fragment(R.layout.fragment_food_for_buy) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buyLists = ArrayList<BuyList>()
        buyLists.add(BuyList("Birthday", Date(), ArrayList()))
        buyLists.add(BuyList("Week", Date(), ArrayList()))
        buyLists.add(BuyList("Country", Date(), ArrayList()))

        val adapter = BuyListRecyclerViewAdapter(context!!, buyLists)
        buylists_recyclerview.adapter = adapter

        buylists_recyclerview.layoutManager = LinearLayoutManager(context!!,
                LinearLayoutManager.VERTICAL, false)

    }
}
