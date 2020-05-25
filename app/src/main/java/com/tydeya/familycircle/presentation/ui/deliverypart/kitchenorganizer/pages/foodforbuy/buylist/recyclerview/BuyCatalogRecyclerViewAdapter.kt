package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.CardviewFoodInBuysCatalogBinding

class BuyCatalogRecyclerViewAdapter(
        private var products: ArrayList<Food>,
        private var isEditableMode: Boolean,
        private val onClickListenerInBuyList: FoodInBuyListViewHolderClickListener)
    :
        RecyclerView.Adapter<FoodViewHolder>(), SwipeToDeleteCallbackListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(CardviewFoodInBuysCatalogBinding.inflate(LayoutInflater.from(parent.context),
                parent, false), onClickListenerInBuyList)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(products[position], isEditableMode)
    }

    fun refreshData(products: ArrayList<Food>) {
        val diffResult = DiffUtil.calculateDiff(BuyCatalogFoodsDiffUtilCallback(this.products, products))

        this.products.clear()
        this.products.addAll(products)

        diffResult.dispatchUpdatesTo(this)
    }

    fun switchMode(isEditableMode: Boolean) {
        if (this.isEditableMode != isEditableMode) {
            this.isEditableMode = isEditableMode
            notifyDataSetChanged()
        }
    }

    override fun onSwipe(position: Int) {
        if (position in products.indices) {
            onClickListenerInBuyList.onFoodVHDeleteClick(products[position].id)
        }
    }

    override fun getItemCount(): Int = products.size

}