package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.FragmentFoodInFridgeBinding
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
        binding.foodInFridgeFloatingButton.attachToRecyclerView(binding.foodInFridgeRecyclerview)
        binding.foodInFridgeFloatingButton.setOnClickListener {
            val addFoodDialog = FridgeAddFoodDialog()
            addFoodDialog.show(childFragmentManager, FRIDGE_ADD_FOOD_DIALOG)
        }
    }

    override fun onFoodInFridgeVHDeleteClick(title: String) {
        val deleteFoodInFridgeDialog = DeleteFoodInFridgeDialog(title)
        deleteFoodInFridgeDialog.show(childFragmentManager, FOOD_IN_FRIDGE_DELETE_DIALOG)
    }

}
