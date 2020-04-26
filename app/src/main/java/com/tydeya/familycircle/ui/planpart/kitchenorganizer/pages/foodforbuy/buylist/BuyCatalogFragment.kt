package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_ID
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.FragmentBuyCatalogBinding
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.eventlistener.KitchenBuyCatalogEventListener
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.BuyCatalogRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.FoodInBuyListViewHolderClickListener
import javax.inject.Inject

class BuyCatalogFragment : Fragment(R.layout.fragment_buy_catalog), KitchenOrganizerCallback,
        FoodInBuyListViewHolderClickListener, BuyCatalogSettingsDialogCallback {

    @Inject
    lateinit var kitchenInteractor: KitchenOrganizerInteractor

    private lateinit var buyCatalogID: String

    private lateinit var adapter: BuyCatalogRecyclerViewAdapter

    private lateinit var buyCatalogEventListener: KitchenBuyCatalogEventListener

    private var editableModeIsActive = false

    // View binding

    private var _binding: FragmentBuyCatalogBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBuyCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectBuyCatalogFragment(this)

        buyCatalogID = requireArguments().getString(BUNDLE_ID)!!
        val buyCatalog = kitchenInteractor.requireCatalogData(buyCatalogID)

        setRecyclerView(buyCatalog.products)

        binding.buyListFloatingButton.attachToRecyclerView(binding.buyListRecyclerview)
        binding.buyListFloatingButton.setOnClickListener {
            editableModeIsActive = !editableModeIsActive
            switchMode()
        }

        setToolbar(buyCatalog.title)

        buyCatalogEventListener = KitchenBuyCatalogEventListener(buyCatalogID, kitchenInteractor)
        buyCatalogEventListener.register()

        binding.buyListAddButton.setOnClickListener {
            val newProductDialog = CreateNewProductDialog(buyCatalogID)
            newProductDialog.show(parentFragmentManager, "dialog_new_product")
        }

        binding.buyListPrimarySettings.setOnClickListener {
            val buyCatalogSettingsDialog = BuyCatalogSettingsDialog(buyCatalogID, this)
            buyCatalogSettingsDialog.show(parentFragmentManager, "dialog_settings")
        }

    }

    /**
     * prepare Ui
     * */

    private fun setRecyclerView(products: ArrayList<Food>) {
        adapter = BuyCatalogRecyclerViewAdapter(requireContext(), products, editableModeIsActive, this)
        binding.buyListRecyclerview.adapter = adapter

        binding.buyListRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
    }

    private fun setToolbar(title: String) {
        binding.buyListToolbar.title = title
        binding.buyListToolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
            buyCatalogEventListener.unregister()
        }
    }

    /**
     * Switch mode
     * */

    private fun switchMode() {
        if (editableModeIsActive) {
            binding.buyListFloatingButton.setImageResource(R.drawable.ic_close_white_24dp)
            binding.buyListAddButton.visibility = View.VISIBLE
            binding.buyListPrimarySettings.visibility = View.VISIBLE
        } else {
            binding.buyListFloatingButton.setImageResource(R.drawable.ic_mode_edit_white_24dp)
            binding.buyListAddButton.visibility = View.GONE
            binding.buyListPrimarySettings.visibility = View.GONE
        }
        adapter.switchMode(editableModeIsActive)
    }

    /**
     * Setting dialog callback
     * */

    override fun onDeleteCatalog() {
        buyCatalogEventListener.unregister()
        NavHostFragment.findNavController(this).popBackStack()
        kitchenInteractor.deleteCatalog(buyCatalogID)
    }

    /**
     * Data updates
     * */

    override fun kitchenDataFromServerUpdated() {
        val buyCatalog = kitchenInteractor.requireCatalogData(buyCatalogID)
        if (buyCatalog.title == "...") {
            NavHostFragment.findNavController(this).popBackStack()
        }
        binding.buyListToolbar.title = buyCatalog.title
        adapter.refreshData(buyCatalog.products)
    }

    /**
     * Recycler callbacks
     * */

    override fun onFoodVHDeleteClick(title: String) {
        kitchenInteractor.deleteProductInCatalog(buyCatalogID, title)
    }

    override fun onFoodVHEditDataClick(title: String) {
        val editProductDialog = EditProductDataDialog(buyCatalogID, title)
        editProductDialog.show(parentFragmentManager, "dialog_edit_product")
    }

    override fun onFoodVHCheckBoxClicked(title: String) {
        kitchenInteractor.buyProduct(buyCatalogID, title)
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
        _binding = null
    }

}
