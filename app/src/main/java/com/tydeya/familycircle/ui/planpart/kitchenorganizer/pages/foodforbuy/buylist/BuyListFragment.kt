package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.BuyListRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_buy_list.*

class BuyListFragment : Fragment(R.layout.fragment_buy_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val products = ArrayList<Food>()
        val adapter = BuyListRecyclerViewAdapter(context!!, products)

        products.add(Food("Банан", "...", .0,.0,.0))
        products.add(Food("Яблоко", "...", .0,.0,.0))
        products.add(Food("Газировка", "...", .0,.0,.0))

        buy_list_recyclerview.adapter = adapter

        buy_list_recyclerview.layoutManager = LinearLayoutManager(context!!,
                LinearLayoutManager.VERTICAL, false)

        buy_list_floating_button.attachToRecyclerView(buy_list_recyclerview)
    }


}
