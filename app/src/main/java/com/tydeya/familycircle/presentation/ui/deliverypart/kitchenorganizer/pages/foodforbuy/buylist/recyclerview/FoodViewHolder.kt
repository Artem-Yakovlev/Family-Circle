package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.databinding.CardviewFoodInBuysCatalogBinding

class FoodViewHolder(private val binding: CardviewFoodInBuysCatalogBinding,
                     private val listenerInBuyList: FoodInBuyListViewHolderClickListener)
    : RecyclerView.ViewHolder(binding.root) {

    fun bindData(food: Food, isEditableMode: Boolean) {

        binding.buyListFoodCardEdit.setOnClickListener {
            listenerInBuyList.onFoodVHEditDataClick(food)
        }

        when (food.foodStatus) {
            FoodStatus.IN_FRIDGE -> {
                binding.buyListFoodCardCheckbox.isChecked = true
                binding.buyListFoodCardCheckbox.isEnabled = false
                binding.buyListFoodCardEdit.visibility = View.GONE
            }
            FoodStatus.NEED_BUY -> {
                binding.buyListFoodCardCheckbox.isChecked = false
                binding.buyListFoodCardCheckbox.isEnabled = true
                binding.buyListFoodCardEdit.visibility = getEditableVisibility(isEditableMode)
            }
        }

        binding.buyListFoodCardCheckbox.setOnClickListener {
            listenerInBuyList.onFoodVHCheckBoxClicked(food)
        }

        initFoodTitle(food)


    }

    private fun initFoodTitle(food: Food) {
        val measure = itemView.context.resources
                .getStringArray(R.array.quantitative_type_of_product)[food.measureType.ordinal]

        if (food.measureType.ordinal != 0) {
            binding.buyListFoodCardTitle.text = itemView.context.getString(
                    R.string.kitchen_organizer_buys_catalog_food_title_and_measure_placeholder,
                    food.title, food.quantityOfMeasure.toString(), measure)
        } else {
            binding.buyListFoodCardTitle.text = food.title
        }
        binding.buyListFoodCardTitle.isSelected = true
    }

    private fun getEditableVisibility(isEditableMode: Boolean) = if (isEditableMode) {
        View.VISIBLE
    } else {
        View.GONE
    }

}

interface FoodInBuyListViewHolderClickListener {

    fun onFoodVHDeleteClick(productId: String)

    fun onFoodVHEditDataClick(food: Food)

    fun onFoodVHCheckBoxClicked(food: Food)
}