package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food

class BuyCatalogRecyclerViewAdapter(val context: Context,
                                    var products: ArrayList<Food>,
                                    var isEditableMode: Boolean,
                                    val onDeleteClickListener: FoodViewHolderDeleteClickListener)
    :
        RecyclerView.Adapter<FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
            FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.buy_list_food_card,
                    parent, false), onDeleteClickListener)

    override fun getItemCount(): Int = products.size


    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(products[position], 0, isEditableMode)
    }

    fun refreshData(actualProducts: ArrayList<Food>) {
        products = actualProducts
        notifyDataSetChanged()
    }

    fun switchMode(isEditableMode: Boolean) {
        if (this.isEditableMode != isEditableMode) {
            this.isEditableMode = isEditableMode
            notifyDataSetChanged()
        }
    }

}