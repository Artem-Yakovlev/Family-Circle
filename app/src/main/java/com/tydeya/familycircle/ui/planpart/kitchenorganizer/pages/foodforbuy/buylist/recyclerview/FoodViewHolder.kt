package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import kotlinx.android.synthetic.main.buy_list_food_card.view.*

class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(food: Food, itemType: Int) {
        itemView.buy_list_food_card_title.text = food.title
    }

}