package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food

class FoodInFridgeRecyclerViewAdapter(val context: Context, var foods: List<Food>): RecyclerView.Adapter<FoodInFridgeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodInFridgeViewHolder =
            FoodInFridgeViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.food_in_fridge_cardview, parent, false))

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: FoodInFridgeViewHolder, position: Int) {
        holder.bindData(foods[position])
    }
}