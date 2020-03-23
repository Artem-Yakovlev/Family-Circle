package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.BuyCatalogsRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.OnBuyCatalogClickListener
import kotlinx.android.synthetic.main.fragment_food_for_buy.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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
        food_for_buy_recyclerview.adapter = adapter

        food_for_buy_recyclerview.layoutManager = LinearLayoutManager(context!!,
                LinearLayoutManager.VERTICAL, false)

        buy_list_floating_button.attachToRecyclerView(food_for_buy_recyclerview)
    }

    override fun onBuyCatalogClick(position: Int) {
        val navController = NavHostFragment.findNavController(this)
        val bundle = Bundle()
        bundle.putInt("position", position)
        navController.navigate(R.id.buyListFragment, bundle)
    }

    override fun kitchenDataFromServerUpdated() {
        Log.d("ASMR", "${kitchenOrganizerInteractor.buyCatalogs[0].products[0].title}")
        adapter.refreshData(kitchenOrganizerInteractor.buyCatalogs)
        adapter.notifyDataSetChanged()
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
