package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodinfridge

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.leinardi.android.speeddial.SpeedDialView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.MeasureType
import com.tydeya.familycircle.databinding.FragmentFoodInFridgeBinding
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.barcodescanner.BarcodeScannerActivity
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.SwipeToDeleteCallback
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodinfridge.recyclerview.FoodInFridgeRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodinfridge.recyclerview.FoodInFridgeViewHolderClickListener
import com.tydeya.familycircle.presentation.viewmodel.FoodInFridgeViewModel
import com.tydeya.familycircle.utils.Resource


class FoodInFridgeFragment
    :
        Fragment(R.layout.fragment_food_in_fridge), FoodInFridgeViewHolderClickListener {

    companion object {
        private const val FRIDGE_ADD_FOOD_DIALOG = "fridge_add_food_dialog"
        private const val FRIDGE_EDIT_FOOD_DIALOG = "fridge_edit_food_dialog"
        private const val FRIDGE_EAT_FOOD_WITH_TYPE_DIALOG = "fridge_eat_food_with_type_dialog"
        private const val FRIDGE_EAT_FOOD_WITHOUT_TYPE_DIALOG = "fridge_eat_food_without_type_dialog"
    }

    private lateinit var adapter: FoodInFridgeRecyclerViewAdapter

    private var _binding: FragmentFoodInFridgeBinding? = null
    private val binding get() = _binding!!

    private lateinit var foodInFridgeViewModel: FoodInFridgeViewModel

    private lateinit var swipeToDeleteCallback: SwipeToDeleteCallback

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

        swipeToDeleteCallback = SwipeToDeleteCallback(requireContext(), adapter, true)
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.foodInFridgeRecyclerview)

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
                            val addFoodInFridgeDialog = AddFoodInFridgeManuallyDialog.newInstance()
                            addFoodInFridgeDialog.show(childFragmentManager, FRIDGE_ADD_FOOD_DIALOG)
                            binding.foodInFridgeFloatingButton.close()
                            return@OnActionSelectedListener true
                        }
                        R.id.add_from_barcode -> {
                            val permissionStatus = ContextCompat.checkSelfPermission(requireContext(),
                                    Manifest.permission.CAMERA)
                            binding.foodInFridgeFloatingButton.close()
                            when {
                                permissionStatus == PackageManager.PERMISSION_GRANTED -> {
                                    val intent = Intent(context, BarcodeScannerActivity::class.java)
                                    startActivity(intent)
                                }
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        requireActivity(), Manifest.permission.CAMERA) -> {
                                    Toast.makeText(requireContext(),
                                            getString(R.string.barcode_scanner_permission_text),
                                            Toast.LENGTH_LONG).show()
                                }
                                else -> {
                                    ActivityCompat.requestPermissions(requireActivity(),
                                            arrayOf(Manifest.permission.CAMERA), 200)
                                }
                            }

                            return@OnActionSelectedListener true
                        }
                    }
                    return@OnActionSelectedListener false
                })
    }

    override fun onFoodInFridgeVhDeleteClick(productId: String) {
        foodInFridgeViewModel.deleteFromFridgeBadFood(productId)
    }

    override fun onFoodInFridgeVhEditClick(food: Food) {
        val editFoodInFridgeDialog = EditFoodInFridgeDialog.newInstance(food)
        editFoodInFridgeDialog.show(childFragmentManager, FRIDGE_EDIT_FOOD_DIALOG)
    }

    override fun onFoodInFridgeVhEatClick(food: Food) {
        if (food.measureType == MeasureType.NOT_CHOSEN) {
            val eatFoodFromFridgeDialog = EatFoodWithoutMeasureTypeFromFridge.newInstance(food)
            eatFoodFromFridgeDialog.show(childFragmentManager, FRIDGE_EAT_FOOD_WITHOUT_TYPE_DIALOG)
        } else {
            val eatFoodFromFridgeDialog = EatFoodFromFridgeDialog.newInstance(food)
            eatFoodFromFridgeDialog.show(childFragmentManager, FRIDGE_EAT_FOOD_WITH_TYPE_DIALOG)
        }
    }
}
