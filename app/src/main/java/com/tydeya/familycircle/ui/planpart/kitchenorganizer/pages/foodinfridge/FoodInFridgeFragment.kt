package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.kitchendatastatus.KitchenDataStatus
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.abstraction.KitchenOrganizerCallback
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

        food_in_fridge_recyclerview.setAdapter(adapter,
                LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false))

        food_in_fridge_floating_button.attachToRecyclerView(food_in_fridge_recyclerview.getRecyclerView())

        food_in_fridge_floating_button.setOnClickListener {
            val addFoodDialog = FridgeAddFoodDialog()
            addFoodDialog.show(parentFragmentManager, "fridge_add_food_dialog")
        }

    }

    override fun kitchenDataFromServerUpdated() {
        if (kitchenOrganizerInteractor.foodsInFridgeStatus == KitchenDataStatus.DATA_RECEIVED) {
            adapter.refreshData(kitchenOrganizerInteractor.foodsInFridge)
            food_in_fridge_recyclerview.unVeil()
        }
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
