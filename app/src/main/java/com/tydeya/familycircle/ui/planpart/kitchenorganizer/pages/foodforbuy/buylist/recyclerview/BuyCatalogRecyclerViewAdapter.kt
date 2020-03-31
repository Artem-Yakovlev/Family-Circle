package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food

class BuyCatalogRecyclerViewAdapter(private val context: Context,
                                    private var products: ArrayList<Food>,
                                    private var isEditableMode: Boolean,
                                    private val onClickListenerInBuyList: FoodInBuyListViewHolderClickListener)
    :
        RecyclerView.Adapter<FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
            FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_buylist_food,
                    parent, false), onClickListenerInBuyList)

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