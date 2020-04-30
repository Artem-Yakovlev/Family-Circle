package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.tydeya.familycircle.viewmodel.AllBuyCatalogsViewModel

class BuyCatalogFragment : Fragment(), FoodInBuyListViewHolderClickListener {

    private lateinit var buyCatalogID: String

    private lateinit var adapter: BuyCatalogRecyclerViewAdapter

    private var editableModeIsActive = false

    private var _binding: FragmentBuyCatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var allBuyCatalogsViewModel: AllBuyCatalogsViewModel
    private lateinit var buyCatalogViewModel: BuyCatalogViewModel
    private lateinit var buyCatalogViewModelFactory: BuyCatalogViewModelFactory

    companion object {
        private const val DIALOG_SETTINGS = "dialog_settings"
        private const val DIALOG_NEW_PRODUCT = "dialog_new_product"
        private const val DIALOG_EDIT_PRODUCT = "dialog_edit_product"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        buyCatalogID = requireArguments().getString(BUNDLE_ID)!!
        _binding = FragmentBuyCatalogBinding.inflate(inflater, container, false)

        buyCatalogViewModelFactory = BuyCatalogViewModelFactory(buyCatalogID)

        buyCatalogViewModel = ViewModelProviders.of(this, buyCatalogViewModelFactory)
                .get(BuyCatalogViewModel::class.java)

        allBuyCatalogsViewModel = ViewModelProviders.of(this)
                .get(AllBuyCatalogsViewModel::class.java)



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
        initToolbar()
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
            val newProductDialog = CreateNewProductDialog()
            newProductDialog.show(childFragmentManager, DIALOG_NEW_PRODUCT)
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
     * Recycler callbacks
     * */

    override fun onFoodVHDeleteClick(title: String) {
        buyCatalogViewModel.deleteProduct(title)
    }

    override fun onFoodVHEditDataClick(title: String) {
        val editProductDataDialog = EditProductDataDialog.newInstance(title)
        editProductDataDialog.show(childFragmentManager, DIALOG_EDIT_PRODUCT)
    }

    override fun onFoodVHCheckBoxClicked(title: String) {
        buyCatalogViewModel.buyProduct(buyCatalogID, title)
    }

    /**
     * Lifecycle
     * */

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
