package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.CardviewFoodInFridgeBinding

class FoodInFridgeViewHolder(private val binding: CardviewFoodInFridgeBinding,
                             private val listener: FoodInFridgeViewHolderClickListener) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(food: Food) {
        binding.foodInFridgeTitle.text = food.title
        binding.foodInFridgeDelete.setOnClickListener {
            listener.onFoodInFridgeVHDeleteClick(food.title)
        }
    }
}

interface FoodInFridgeViewHolderClickListener {

    fun onFoodInFridgeVHDeleteClick(title: String)
}