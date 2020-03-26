package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.kitchendatastatus.KitchenDataStatus
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.BuyCatalogsRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.OnBuyCatalogClickListener
import kotlinx.android.synthetic.main.fragment_food_for_buy.*
import javax.inject.Inject

class FoodForBuyFragment : Fragment(R.layout.fragment_food_for_buy), OnBuyCatalogClickListener,
        KitchenOrganizerCallback {

    @Inject
    lateinit var kitchenOrganizerInteractor: KitchenOrganizerInteractor

    lateinit var adapter: BuyCatalogsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFoodForBuyFragment(this)

        adapter = BuyCatalogsRecyclerViewAdapter(context!!,
                kitchenOrganizerInteractor.buyCatalogs, this)
        food_for_buy_recyclerview.setAdapter(adapter,
                LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false))
        food_for_buy_recyclerview.addVeiledItems(12)

        buy_list_floating_button.attachToRecyclerView(food_for_buy_recyclerview.getRecyclerView())
        buy_list_floating_button.setOnClickListener {
            val newListDialog = CreateBuyListDialog()
            newListDialog.show(parentFragmentManager, "dialog_new_list")
        }
    }

    override fun onBuyCatalogClick(position: Int) {
        val navController = NavHostFragment.findNavController(this)
        val bundle = Bundle()
        bundle.putString("id", kitchenOrganizerInteractor.buyCatalogs[position].id)
        navController.navigate(R.id.buyListFragment, bundle)
    }

    override fun kitchenDataFromServerUpdated() {
        if (kitchenOrganizerInteractor.buyCatalogsStatus == KitchenDataStatus.DATA_RECEIVED) {
            adapter.refreshData(kitchenOrganizerInteractor.buyCatalogs)
            adapter.notifyDataSetChanged()
            food_for_buy_recyclerview.unVeil()
        }
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
