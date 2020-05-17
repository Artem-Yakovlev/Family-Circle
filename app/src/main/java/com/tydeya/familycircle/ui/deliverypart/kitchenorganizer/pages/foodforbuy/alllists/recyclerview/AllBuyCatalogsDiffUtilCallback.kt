package com.tydeya.familycircle.ui.deliverypart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog

class AllBuyCatalogsDiffUtilCallback(
        private val oldList: List<BuyCatalog>,
        private val newList: List<BuyCatalog>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].title == newList[newItemPosition].title &&
                    oldList[oldItemPosition].dateOfCreate == newList[newItemPosition].dateOfCreate

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}