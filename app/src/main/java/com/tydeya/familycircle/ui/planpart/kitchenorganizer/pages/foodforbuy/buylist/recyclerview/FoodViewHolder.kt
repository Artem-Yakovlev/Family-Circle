package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import kotlinx.android.synthetic.main.buy_list_food_card.view.*

class FoodViewHolder(itemView: View, private val listenerInBuyList: FoodInBuyListViewHolderClickListener) : RecyclerView.ViewHolder(itemView) {

    fun bindData(food: Food, itemType: Int, isEditableMode: Boolean) {
        itemView.buy_list_food_card_title.text = food.title

        itemView.buy_list_food_card_checkbox.isChecked = when (food.foodStatus) {
            FoodStatus.NEED_BUY -> false
            FoodStatus.IN_FRIDGE -> true
        }

        itemView.buy_list_food_card_delete.visibility = getEditableVisibility(isEditableMode)

        itemView.buy_list_food_card_edit.visibility = getEditableVisibility(isEditableMode)

        itemView.buy_list_food_card_delete
                .setOnClickListener { listenerInBuyList.onFoodVHDeleteClick(food.title) }

        itemView.buy_list_food_card_edit
                .setOnClickListener { listenerInBuyList.onFoodVHEditDataClick(food.title) }

        itemView.buy_list_food_card_checkbox.setOnClickListener {
            itemView.buy_list_food_card_checkbox.isClickable = false
            itemView.buy_list_food_card_checkbox.isFocusable = false
            listenerInBuyList.onFoodVHCheckBoxClicked(food.title)
        }
    }

    private fun getEditableVisibility(isEditableMode: Boolean) = if (isEditableMode) {
        View.VISIBLE
    } else {
        View.GONE
    }

}

interface FoodInBuyListViewHolderClickListener {

    fun onFoodVHDeleteClick(title: String)

    fun onFoodVHEditDataClick(title: String)

    fun onFoodVHCheckBoxClicked(title: String)
}