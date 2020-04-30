package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.databinding.CardviewBuycatalogFoodBinding

class FoodViewHolder(private val binding: CardviewBuycatalogFoodBinding,
                     private val listenerInBuyList: FoodInBuyListViewHolderClickListener)
    : RecyclerView.ViewHolder(binding.root) {

    fun bindData(food: Food, itemType: Int, isEditableMode: Boolean) {
        binding.buyListFoodCardTitle.text = food.title

        binding.buyListFoodCardDelete.visibility = getEditableVisibility(isEditableMode)

        binding.buyListFoodCardEdit.visibility = getEditableVisibility(isEditableMode)

        binding.buyListFoodCardDelete.setOnClickListener {
            listenerInBuyList.onFoodVHDeleteClick(food.title)
        }

        binding.buyListFoodCardEdit.setOnClickListener {
            listenerInBuyList.onFoodVHEditDataClick(food.title)
        }

        when (food.foodStatus) {
            FoodStatus.IN_FRIDGE -> {
                binding.buyListFoodCardCheckbox.isChecked = true
                binding.buyListFoodCardCheckbox.isEnabled = false
            }
            FoodStatus.NEED_BUY -> {
                binding.buyListFoodCardCheckbox.isChecked = false
            }
        }

        binding.buyListFoodCardCheckbox.setOnClickListener {
            binding.buyListFoodCardCheckbox.isClickable = false
            binding.buyListFoodCardCheckbox.isFocusable = false
            listenerInBuyList.onFoodVHCheckBoxClicked(food.title)
        }
    }

    private fun getEditableVisibility(isEditableMode: Boolean) = if (isEditableMode) {
        View.VISIBLE
    } else {
        View.GONE
    }

}

interface FoodInBuyListViewHolderClickListener {

    fun onFoodVHDeleteClick(title: String)

    fun onFoodVHEditDataClick(title: String)

    fun onFoodVHCheckBoxClicked(title: String)
}