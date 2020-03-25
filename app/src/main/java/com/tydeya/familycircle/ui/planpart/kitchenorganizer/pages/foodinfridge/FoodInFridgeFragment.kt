package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App

import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview.FoodInFridgeRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview.FoodInFridgeViewHolderClickListener
import kotlinx.android.synthetic.main.fragment_food_in_fridge.*
import javax.inject.Inject


class FoodInFridgeFragment
    :
        Fragment(R.layout.fragment_food_in_fridge),
        KitchenOrganizerCallback,
        FoodInFridgeViewHolderClickListener {

    @Inject
    lateinit var kitchenOrganizerInteractor: KitchenOrganizerInteractor

    private lateinit var adapter: FoodInFridgeRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFoodInFridgeFragment(this)

        adapter = FoodInFridgeRecyclerViewAdapter(context!!,
                kitchenOrganizerInteractor.foodsInFridge, this)

        food_in_fridge_recyclerview.adapter = adapter
        food_in_fridge_recyclerview.layoutManager =
                LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
    }

    override fun kitchenDataFromServerUpdated() {
        adapter.refreshData(kitchenOrganizerInteractor.foodsInFridge)
    }

    override fun onFoodInFridgeVHDeleteClick(title: String) {
        val deleteFoodInFridgeDialog = DeleteFoodInFridgeDialog(title)
        deleteFoodInFridgeDialog.show(parentFragmentManager, "food_in_fridge_delete_dialog")
    }

    override fun onResume() {
        super.onResume()
        kitchenOrganizerInteractor.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        kitchenOrganizerInteractor.unsubscribe(this)
    }

}
