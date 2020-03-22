package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.BuyCatalogRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_buy_list.*

class BuyCatalogFragment : Fragment(R.layout.fragment_buy_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val products = ArrayList<Food>()
        val adapter = BuyCatalogRecyclerViewAdapter(context!!, products)

        products.add(Food("Банан", "...", .0, .0, .0))
        products.add(Food("Яблоко", "...", .0, .0, .0))
        products.add(Food("Газировка", "...", .0, .0, .0))

        buy_list_recyclerview.adapter = adapter

        buy_list_recyclerview.layoutManager = LinearLayoutManager(context!!,
                LinearLayoutManager.VERTICAL, false)

        buy_list_floating_button.attachToRecyclerView(buy_list_recyclerview)

        buy_list_toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        buy_list_toolbar.title = "Birth day"
    }


}
