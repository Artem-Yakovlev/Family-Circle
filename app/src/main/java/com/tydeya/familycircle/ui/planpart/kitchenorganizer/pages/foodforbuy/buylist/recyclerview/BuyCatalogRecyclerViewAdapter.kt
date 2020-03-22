package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food

class BuyCatalogRecyclerViewAdapter(val context: Context, var products: List<Food>) :
        RecyclerView.Adapter<FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
            FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.buy_list_food_card,
                    parent, false))

    override fun getItemCount(): Int = products.size


    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(products[position], 0)
    }
}