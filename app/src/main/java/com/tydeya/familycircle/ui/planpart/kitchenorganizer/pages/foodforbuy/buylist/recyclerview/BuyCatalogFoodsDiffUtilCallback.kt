package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.kitchenorganizer.food.Food

class BuyCatalogFoodsDiffUtilCallback(
        private val oldList: List<Food>,
        private val newList: List<Food>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].title == newList[newItemPosition].title

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}