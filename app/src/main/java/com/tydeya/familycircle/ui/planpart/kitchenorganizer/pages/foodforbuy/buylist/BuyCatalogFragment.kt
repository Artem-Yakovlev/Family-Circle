package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.BuyCatalogRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_buy_list.*
import javax.inject.Inject

class BuyCatalogFragment : Fragment(R.layout.fragment_buy_list) {

    @Inject
    lateinit var kitchenInteractor: KitchenOrganizerInteractor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectBuyCatalogFragment(this)

        val position = arguments!!.getInt("position")
        val adapter = BuyCatalogRecyclerViewAdapter(context!!, kitchenInteractor.buyCatalogs[position].products)

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
