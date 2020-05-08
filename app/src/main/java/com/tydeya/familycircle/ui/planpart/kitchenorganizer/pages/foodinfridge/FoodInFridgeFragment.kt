package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.leinardi.android.speeddial.SpeedDialView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.FragmentFoodInFridgeBinding
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.BarcodeScannerActivity
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview.FoodInFridgeRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview.FoodInFridgeViewHolderClickListener
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.viewmodel.FoodInFridgeViewModel


class FoodInFridgeFragment
    :
        Fragment(R.layout.fragment_food_in_fridge), FoodInFridgeViewHolderClickListener {

    companion object {
        private const val FOOD_IN_FRIDGE_DELETE_DIALOG = "food_in_fridge_delete_dialog"
        private const val FRIDGE_ADD_FOOD_DIALOG = "fridge_add_food_dialog"
    }

    private lateinit var adapter: FoodInFridgeRecyclerViewAdapter

    private var _binding: FragmentFoodInFridgeBinding? = null
    private val binding get() = _binding!!

    private lateinit var foodInFridgeViewModel: FoodInFridgeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentFoodInFridgeBinding.inflate(inflater, container, false)
        foodInFridgeViewModel = ViewModelProvider(this).get(FoodInFridgeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initFloatingButton()
    }

    private fun initRecyclerView() {
        adapter = FoodInFridgeRecyclerViewAdapter(ArrayList(), this)
        binding.foodInFridgeRecyclerview.adapter = adapter
        binding.foodInFridgeRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
        foodInFridgeViewModel.products.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    adapter.refreshData(it.data)
                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                }
            }
        })
    }

    private fun initFloatingButton() {
        binding.foodInFridgeFloatingButton.inflate(R.menu.food_in_fridge_speed_dial_menu)

        binding.foodInFridgeFloatingButton
                .setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
                    when (actionItem.id) {
                        R.id.simple_add -> {
                            val addFoodDialog = FridgeAddFoodDialog()
                            addFoodDialog.show(childFragmentManager, FRIDGE_ADD_FOOD_DIALOG)
                            binding.foodInFridgeFloatingButton.close()
                            return@OnActionSelectedListener true
                        }
                        R.id.add_from_barcode -> {
                            val intent = Intent(context, BarcodeScannerActivity::class.java)
                            startActivity(intent)
                            binding.foodInFridgeFloatingButton.close()
                            return@OnActionSelectedListener true
                        }
                    }
                    false
                })
    }

    override fun onFoodInFridgeVHDeleteClick(productId: String) {
        val deleteFoodInFridgeDialog = DeleteFoodInFridgeDialog.newInstance(productId)
        deleteFoodInFridgeDialog.show(childFragmentManager, FOOD_IN_FRIDGE_DELETE_DIALOG)
    }

}
