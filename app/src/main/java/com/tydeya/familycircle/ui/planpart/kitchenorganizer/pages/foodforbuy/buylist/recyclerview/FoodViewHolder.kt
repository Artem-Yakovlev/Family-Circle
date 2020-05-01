package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.databinding.CardviewFoodInBuysCatalogBinding

class FoodViewHolder(private val binding: CardviewFoodInBuysCatalogBinding,
                     private val listenerInBuyList: FoodInBuyListViewHolderClickListener)
    : RecyclerView.ViewHolder(binding.root) {

    fun bindData(food: Food, itemType: Int, isEditableMode: Boolean) {
        binding.buyListFoodCardTitle.text = food.title

        binding.buyListFoodCardDelete.visibility = getEditableVisibility(isEditableMode)

        binding.buyListFoodCardEdit.visibility = getEditableVisibility(isEditableMode)

        binding.buyListFoodCardDelete.setOnClickListener {
            listenerInBuyList.onFoodVHDeleteClick(food.id)
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
            listenerInBuyList.onFoodVHCheckBoxClicked(food)
        }

        val measure = itemView.context.resources
                .getStringArray(R.array.quantitative_type_of_product)[food.measureType.ordinal]

        if (food.measureType.ordinal != 0) {
            binding.buyListFoodCardQuantityAndMeasure.text = itemView.context.getString(
                    R.string.kitchen_organizer_buys_catalog_food_quantity_and_measure_placeholder,
                    food.quantityOfMeasure.toString(), measure)
        } else {
            binding.buyListFoodCardQuantityAndMeasure.visibility = View.GONE
        }


    }

    private fun getEditableVisibility(isEditableMode: Boolean) = if (isEditableMode) {
        View.VISIBLE
    } else {
        View.GONE
    }

}

interface FoodInBuyListViewHolderClickListener {

    fun onFoodVHDeleteClick(productId: String)

    fun onFoodVHEditDataClick(title: String)

    fun onFoodVHCheckBoxClicked(productId: Food)
}