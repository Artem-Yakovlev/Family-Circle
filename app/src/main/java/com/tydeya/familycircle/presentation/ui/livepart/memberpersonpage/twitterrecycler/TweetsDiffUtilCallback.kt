package com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.twitterrecycler

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.familymember.Tweet
import com.tydeya.familycircle.data.kitchenorganizer.food.Food

class TweetsDiffUtilCallback(
        private val oldList: List<Tweet>,
        private val newList: List<Tweet>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}
