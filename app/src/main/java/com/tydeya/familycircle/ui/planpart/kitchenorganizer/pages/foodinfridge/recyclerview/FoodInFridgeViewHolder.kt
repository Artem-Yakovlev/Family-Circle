package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.MeasureType
import com.tydeya.familycircle.databinding.CardviewFoodInFridgeBinding

class FoodInFridgeViewHolder(private val binding: CardviewFoodInFridgeBinding,
                             private val listener: FoodInFridgeViewHolderClickListener) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(food: Food) {
        initFoodTitle(food)
    }

    private fun initFoodTitle(food: Food) {
        val measure = itemView.context.resources
                .getStringArray(R.array.quantitative_type_of_product)[food.measureType.ordinal]

        if (food.measureType.ordinal != 0) {
            binding.foodInFridgeTitle.text = itemView.context.getString(
                    R.string.kitchen_organizer_buys_catalog_food_title_and_measure_placeholder,
                    food.title, food.quantityOfMeasure.toString(), measure)
        } else {
            binding.foodInFridgeTitle.text = food.title
        }
        binding.foodInFridgeTitle.isSelected = true

        binding.editFoodButton.setOnClickListener {
            listener.onFoodInFridgeVhEditClick(food)
        }

        binding.eatFoodButton.setOnClickListener {
            listener.onFoodInFridgeVhEatClick(food)
        }

        if (food.measureType == MeasureType.NOT_CHOSEN) {
            binding.eatFoodButton.visibility = View.INVISIBLE
        }
    }
}

interface FoodInFridgeViewHolderClickListener {

    fun onFoodInFridgeVhDeleteClick(productId: String)

    fun onFoodInFridgeVhEditClick(food: Food)

    fun onFoodInFridgeVhEatClick(food: Food)
}