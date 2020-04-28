package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_ID
import com.tydeya.familycircle.databinding.FragmentBuyCatalogBinding
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.BuyCatalogRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.FoodInBuyListViewHolderClickListener
import com.tydeya.familycircle.viewmodel.BuyCatalogViewModel
import com.tydeya.familycircle.viewmodel.BuyCatalogViewModelFactory
import com.tydeya.familycircle.utils.Resource

class BuyCatalogFragment : Fragment(), FoodInBuyListViewHolderClickListener,
        BuyCatalogSettingsDialogCallback {

    private lateinit var buyCatalogID: String

    private lateinit var adapter: BuyCatalogRecyclerViewAdapter

    private var editableModeIsActive = false

    private var _binding: FragmentBuyCatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var buyCatalogViewModel: BuyCatalogViewModel
    private lateinit var buyCatalogViewModelFactory: BuyCatalogViewModelFactory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        buyCatalogID = requireArguments().getString(BUNDLE_ID)!!
        _binding = FragmentBuyCatalogBinding.inflate(inflater, container, false)

        buyCatalogViewModelFactory = BuyCatalogViewModelFactory(buyCatalogID)
        buyCatalogViewModel = ViewModelProviders.of(this, buyCatalogViewModelFactory)
                .get(BuyCatalogViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    /**
     * prepare Ui
     * */

    private fun initUi() {
        initRecyclerView()
        initFloatingButton()
        initToolbar("Test")
        initAddButton()
        initSettingsButton()
    }

    private fun initRecyclerView() {
        adapter = BuyCatalogRecyclerViewAdapter(ArrayList(), editableModeIsActive, this)
        binding.productsRecyclerview.adapter = adapter

        binding.productsRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)

        buyCatalogViewModel.products.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    adapter.refreshData(it.data)
                }
                is Resource.Failure -> {

                }
            }
        })
    }

    private fun initToolbar(title: String) {
        binding.toolbar.title = title
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    private fun initSettingsButton() {
        binding.settingsButton.setOnClickListener {
            val buyCatalogSettingsDialog = BuyCatalogSettingsDialog(buyCatalogID, this)
            buyCatalogSettingsDialog.show(parentFragmentManager, "dialog_settings")
        }
    }

    private fun initAddButton() {
        binding.buyListAddButton.setOnClickListener {
//            val newProductDialog = CreateNewProductDialog(buyCatalogID)
//            newProductDialog.show(parentFragmentManager, "dialog_new_product")
        }
    }

    private fun initFloatingButton() {
        binding.floatingButton.attachToRecyclerView(binding.productsRecyclerview)
        binding.floatingButton.setOnClickListener {
            editableModeIsActive = !editableModeIsActive
            switchMode()
        }
    }

    /**
     * Switch mode
     * */

    private fun switchMode() {
        if (editableModeIsActive) {
            binding.floatingButton.setImageResource(R.drawable.ic_close_white_24dp)
            binding.buyListAddButton.visibility = View.VISIBLE
            binding.settingsButton.visibility = View.VISIBLE
        } else {
            binding.floatingButton.setImageResource(R.drawable.ic_mode_edit_white_24dp)
            binding.buyListAddButton.visibility = View.GONE
            binding.settingsButton.visibility = View.GONE
        }
        adapter.switchMode(editableModeIsActive)
    }

    /**
     * Setting dialog callback
     * */

    override fun onDeleteCatalog() {
//        buyCatalogEventListener.unregister()
//        NavHostFragment.findNavController(this).popBackStack()
//        kitchenInteractor.deleteCatalog(buyCatalogID)
    }

    /**
     * Recycler callbacks
     * */

    override fun onFoodVHDeleteClick(title: String) {
//        kitchenInteractor.deleteProductInCatalog(buyCatalogID, title)
    }

    override fun onFoodVHEditDataClick(title: String) {
//        val editProductDialog = EditProductDataDialog(buyCatalogID, title)
//        editProductDialog.show(parentFragmentManager, "dialog_edit_product")
    }

    override fun onFoodVHCheckBoxClicked(title: String) {
//        kitchenInteractor.buyProduct(buyCatalogID, title)
    }

    /**
     * Lifecycle
     * */

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
