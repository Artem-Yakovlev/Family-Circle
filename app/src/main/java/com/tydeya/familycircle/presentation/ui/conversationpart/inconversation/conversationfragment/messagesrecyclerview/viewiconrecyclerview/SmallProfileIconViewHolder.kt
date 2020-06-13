package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.messagesrecyclerview.viewiconrecyclerview

import android.util.Log
import com.bumptech.glide.Glide
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.RecyclerItemChatmessageViewedUserIconBinding
import com.tydeya.familycircle.presentation.ui.utils.BaseViewHolder

class SmallProfileIconViewHolder(
        private val binding: RecyclerItemChatmessageViewedUserIconBinding
) :
        BaseViewHolder<String>(binding.root) {

    override fun bindData(item: String) {
        if (item == "") {
            Glide.with(itemView.context)
                    .load(R.drawable.ic_account_circle_blue_24dp)
                    .into(binding.userImage)
        } else {
            Glide.with(itemView.context)
                    .load(item)
                    .into(binding.userImage)
        }
    }

}
