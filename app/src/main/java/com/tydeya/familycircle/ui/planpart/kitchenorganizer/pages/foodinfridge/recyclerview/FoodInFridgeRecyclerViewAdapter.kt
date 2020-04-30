package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.CardviewFoodInFridgeBinding

class FoodInFridgeRecyclerViewAdapter(
        private var foods: ArrayList<Food>,
        private var listener: FoodInFridgeViewHolderClickListener
) :
        RecyclerView.Adapter<FoodInFridgeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodInFridgeViewHolder =
            FoodInFridgeViewHolder(CardviewFoodInFridgeBinding.inflate(LayoutInflater
                    .from(parent.context), parent, false), listener)

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: FoodInFridgeViewHolder, position: Int) {
        holder.bindData(foods[position])
    }

    fun refreshData(foods: ArrayList<Food>) {
        this.foods = foods
        notifyDataSetChanged()
    }
}