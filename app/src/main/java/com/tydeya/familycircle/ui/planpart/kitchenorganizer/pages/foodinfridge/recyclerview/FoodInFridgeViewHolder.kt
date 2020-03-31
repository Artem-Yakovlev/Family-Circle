package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import kotlinx.android.synthetic.main.cardview_foor_in_fridge.view.*

class FoodInFridgeViewHolder(itemView: View, private val listener: FoodInFridgeViewHolderClickListener) :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(food: Food) {
        itemView.food_in_fridge_title.text = food.title
        itemView.food_in_fridge_delete.setOnClickListener {
            listener.onFoodInFridgeVHDeleteClick(food.title)
        }
    }
}

interface FoodInFridgeViewHolderClickListener {

    fun onFoodInFridgeVHDeleteClick(title: String)
}