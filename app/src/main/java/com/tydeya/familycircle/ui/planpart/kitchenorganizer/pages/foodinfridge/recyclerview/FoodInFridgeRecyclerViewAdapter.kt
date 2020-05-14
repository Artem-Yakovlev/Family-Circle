package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.CardviewFoodInFridgeBinding
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.BuyCatalogFoodsDiffUtilCallback
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview.SwipeToDeleteCallbackListener

class FoodInFridgeRecyclerViewAdapter(
        private var products: ArrayList<Food>,
        private var listener: FoodInFridgeViewHolderClickListener
) :
        RecyclerView.Adapter<FoodInFridgeViewHolder>(), SwipeToDeleteCallbackListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodInFridgeViewHolder =
            FoodInFridgeViewHolder(CardviewFoodInFridgeBinding.inflate(LayoutInflater
                    .from(parent.context), parent, false), listener)

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: FoodInFridgeViewHolder, position: Int) {
        holder.bindData(products[position])
    }

    fun refreshData(products: ArrayList<Food>) {
        val diffResult = DiffUtil.calculateDiff(BuyCatalogFoodsDiffUtilCallback(this.products, products))

        this.products.clear()
        this.products.addAll(products)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onSwipe(position: Int) {
        if (position in products.indices) {
            listener.onFoodInFridgeVhDeleteClick(products[position].id)
        }
    }
}