package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_ID
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.FragmentBuyCatalogBinding
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.BuyCatalogRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.FoodInBuyListViewHolderClickListener
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.SwipeToDeleteCallback
import com.tydeya.familycircle.presentation.viewmodel.kitchen.allcatalogs.AllBuyCatalogsViewModel
import com.tydeya.familycircle.presentation.viewmodel.kitchen.allcatalogs.AllBuyCatalogsViewModelFactory
import com.tydeya.familycircle.presentation.viewmodel.kitchen.buycatalogviewmodel.BuyCatalogViewModel
import com.tydeya.familycircle.presentation.viewmodel.kitchen.buycatalogviewmodel.BuyCatalogViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import com.tydeya.familycircle.utils.extensions.popBackStack

class BuyCatalogFragment : Fragment(), FoodInBuyListViewHolderClickListener {

    private lateinit var buyCatalogID: String

    private lateinit var adapter: BuyCatalogRecyclerViewAdapter
    private lateinit var swipeToDeleteCallback: SwipeToDeleteCallback

    private var isEditableMode = false

    private var _binding: FragmentBuyCatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var allBuyCatalogsViewModel: AllBuyCatalogsViewModel
    private lateinit var buyCatalogViewModel: BuyCatalogViewModel

    companion object {
        private const val DIALOG_SETTINGS = "dialog_settings"
        private const val DIALOG_NEW_PRODUCT = "dialog_new_product"
        private const val DIALOG_EDIT_PRODUCT = "dialog_edit_product"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        buyCatalogID = requireArguments().getString(BUNDLE_ID) ?: ""
        if (buyCatalogID == "") {
            popBackStack()
        }

        _binding = FragmentBuyCatalogBinding.inflate(inflater, container, false)

        buyCatalogViewModel = ViewModelProviders.of(this,
                BuyCatalogViewModelFactory(requireContext().currentFamilyId, buyCatalogID))
                .get(BuyCatalogViewModel::class.java)

        allBuyCatalogsViewModel = ViewModelProviders
                .of(this, AllBuyCatalogsViewModelFactory(requireContext().currentFamilyId))
                .get(AllBuyCatalogsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initFloatingButton()
        initToolbar()
        initAddButton()
        initSettingsButton()
    }

    /**
     * prepare Ui
     * */

    private fun initRecyclerView() {
        adapter = BuyCatalogRecyclerViewAdapter(ArrayList(), isEditableMode, this)
        binding.productsRecyclerview.adapter = adapter

        binding.productsRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)

        swipeToDeleteCallback = SwipeToDeleteCallback(requireContext(), adapter, isEditableMode)
        ItemTouchHelper(swipeToDeleteCallback)
                .attachToRecyclerView(binding.productsRecyclerview)

        buyCatalogViewModel.products.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.loadingCircle.visibility = View.VISIBLE
                    binding.productsRecyclerview.visibility = View.GONE
                }
                is Resource.Success -> {
                    adapter.refreshData(it.data)
                    binding.loadingCircle.visibility = View.GONE
                    binding.productsRecyclerview.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    popBackStack()
                }
            }
        })
    }

    private fun initToolbar() {
        allBuyCatalogsViewModel.buyCatalogsResource.observe(viewLifecycleOwner, Observer {
            binding.toolbar.title = when (it) {
                is Resource.Success -> {
                    when (val titleResource = allBuyCatalogsViewModel.getBuysCatalogTitleById(buyCatalogID)) {
                        is Resource.Success -> titleResource.data
                        is Resource.Failure -> {
                            with(Toast(requireContext())) {
                                view = LayoutInflater.from(requireContext())
                                        .inflate(R.layout.toast_kitchen_organizer_buys_catalog_is_not_exist,
                                                null)
                                duration = Toast.LENGTH_LONG
                                show()
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            ""
                        }
                        else -> ""
                    }
                }
                else -> ""
            }
        })

        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    private fun initSettingsButton() {
        binding.settingsButton.setOnClickListener {
            val buyCatalogSettingsDialog = BuyCatalogSettingsDialog()
            buyCatalogSettingsDialog.show(childFragmentManager, DIALOG_SETTINGS)
        }
    }

    private fun initAddButton() {
        binding.buyListAddButton.setOnClickListener {
            val newProductDialog = CreateNewProductDialog.newInstance()
            newProductDialog.show(childFragmentManager, DIALOG_NEW_PRODUCT)
        }
    }

    private fun initFloatingButton() {
        binding.floatingButton.attachToRecyclerView(binding.productsRecyclerview)
        binding.floatingButton.setOnClickListener {
            switchMode()
        }
    }

    /**
     * Switch mode
     * */

    private fun switchMode() {
        isEditableMode = !isEditableMode
        if (isEditableMode) {
            binding.floatingButton.setImageResource(R.drawable.ic_close_white_24dp)
            binding.buyListAddButton.visibility = View.VISIBLE
            binding.settingsButton.visibility = View.VISIBLE
        } else {
            binding.floatingButton.setImageResource(R.drawable.ic_mode_edit_white_24dp)
            binding.buyListAddButton.visibility = View.GONE
            binding.settingsButton.visibility = View.GONE
        }
        adapter.switchMode(isEditableMode)
        swipeToDeleteCallback.isEditableMode = isEditableMode
    }

    /**
     * Recycler callbacks
     * */

    override fun onFoodVHDeleteClick(productId: String) {
        buyCatalogViewModel.deleteProduct(productId)
    }

    override fun onFoodVHEditDataClick(food: Food) {
        val editProductDataDialog = EditFoodInBuysCatalogDialog.newInstance(food)
        editProductDataDialog.show(childFragmentManager, DIALOG_EDIT_PRODUCT)
    }

    override fun onFoodVHCheckBoxClicked(food: Food) {
        buyCatalogViewModel.buyProduct(food)
    }

    /**
     * Lifecycle
     * */

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
