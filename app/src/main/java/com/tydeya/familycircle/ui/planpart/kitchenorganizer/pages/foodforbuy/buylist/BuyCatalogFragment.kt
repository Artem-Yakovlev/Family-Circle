package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.BuyCatalogRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_buy_list.*
import javax.inject.Inject

class BuyCatalogFragment : Fragment(R.layout.fragment_buy_list), KitchenOrganizerCallback {

    @Inject
    lateinit var kitchenInteractor: KitchenOrganizerInteractor

    private lateinit var buyCatalogID: String

    private lateinit var adapter: BuyCatalogRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectBuyCatalogFragment(this)

        buyCatalogID = arguments!!.getString("id")!!
        val buyCatalog = kitchenInteractor.requireCatalogData(buyCatalogID, true)

        adapter = BuyCatalogRecyclerViewAdapter(context!!, buyCatalog.products)
        buy_list_recyclerview.adapter = adapter

        buy_list_recyclerview.layoutManager = LinearLayoutManager(context!!,
                LinearLayoutManager.VERTICAL, false)

        buy_list_floating_button.attachToRecyclerView(buy_list_recyclerview)

        buy_list_toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
            kitchenInteractor.stopListenCatalogData(buyCatalogID)
        }
        buy_list_toolbar.title = buyCatalog.title
    }

    override fun kitchenDataFromServerUpdated() {
        val buyCatalog = kitchenInteractor.requireCatalogData(buyCatalogID, false)
        buy_list_toolbar.title = buyCatalog.title
        adapter.refreshData(buyCatalog.products)
    }

    override fun onResume() {
        super.onResume()
        kitchenInteractor.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        kitchenInteractor.unsubscribe(this)
    }

}
