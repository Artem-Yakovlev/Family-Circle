package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.NavigateConsts.ID
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.eventlistener.KitchenBuyCatalogEventListener
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.BuyCatalogRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.FoodViewHolderDeleteClickListener
import kotlinx.android.synthetic.main.fragment_buy_list.*
import javax.inject.Inject

class BuyCatalogFragment : Fragment(R.layout.fragment_buy_list), KitchenOrganizerCallback,
        FoodViewHolderDeleteClickListener {

    @Inject
    lateinit var kitchenInteractor: KitchenOrganizerInteractor

    private lateinit var buyCatalogID: String

    private lateinit var adapter: BuyCatalogRecyclerViewAdapter

    private lateinit var buyCatalogEventListener: KitchenBuyCatalogEventListener

    private var editableModeIsActive = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectBuyCatalogFragment(this)

        buyCatalogID = arguments!!.getString(ID)!!
        val buyCatalog = kitchenInteractor.requireCatalogData(buyCatalogID)

        setRecyclerView(buyCatalog.products)

        buy_list_floating_button.attachToRecyclerView(buy_list_recyclerview)
        buy_list_floating_button.setOnClickListener {
            editableModeIsActive = !editableModeIsActive
            switchMode()
        }

        setToolbar(buyCatalog.title)

        buyCatalogEventListener = KitchenBuyCatalogEventListener(buyCatalogID, kitchenInteractor)
        buyCatalogEventListener.register()

        buy_list_add_button.setOnClickListener {
            val newProductDialog = CreateNewProductDialog(buyCatalogID)
            newProductDialog.show(parentFragmentManager, "dialog_new_product")
        }
    }

    /**
     * prepare Ui
     * */

    private fun setRecyclerView(products: ArrayList<Food>) {
        adapter = BuyCatalogRecyclerViewAdapter(context!!, products, editableModeIsActive, this)
        buy_list_recyclerview.adapter = adapter

        buy_list_recyclerview.layoutManager = LinearLayoutManager(context!!,
                LinearLayoutManager.VERTICAL, false)
    }

    private fun setToolbar(title: String) {
        buy_list_toolbar.title = title
        buy_list_toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
            buyCatalogEventListener.unregister()
        }
    }

    /**
     * Switch mode
     * */

    private fun switchMode() {
        if (editableModeIsActive) {
            buy_list_floating_button.setImageResource(R.drawable.ic_close_black_24dp)
            buy_list_add_button.visibility = View.VISIBLE
            buy_list_remove_list_button.visibility = View.VISIBLE
        } else {
            buy_list_floating_button.setImageResource(R.drawable.ic_mode_edit_black_24dp)
            buy_list_add_button.visibility = View.GONE
            buy_list_remove_list_button.visibility = View.GONE
        }
        adapter.switchMode(editableModeIsActive)
    }

    /**
     * Data updates
     * */

    override fun kitchenDataFromServerUpdated() {
        val buyCatalog = kitchenInteractor.requireCatalogData(buyCatalogID)
        buy_list_toolbar.title = buyCatalog.title
        adapter.refreshData(buyCatalog.products)
    }

    /**
     * Recycler callbacks
     * */

    override fun onFoodVHDeleteClick(title: String) {
        kitchenInteractor.deleteProduct(buyCatalogID, title)
    }

    /**
     * Lifecycle
     * */

    override fun onResume() {
        super.onResume()
        kitchenInteractor.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        kitchenInteractor.unsubscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        buyCatalogEventListener.unregister()
    }

}
