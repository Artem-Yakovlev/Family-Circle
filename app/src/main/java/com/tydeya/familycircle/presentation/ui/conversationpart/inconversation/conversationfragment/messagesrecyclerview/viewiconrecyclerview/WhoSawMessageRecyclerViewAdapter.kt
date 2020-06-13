package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.messagesrecyclerview.viewiconrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.databinding.RecyclerItemChatmessageViewedUserIconBinding
import com.tydeya.familycircle.presentation.ui.utils.BaseViewHolder

class WhoSawMessageRecyclerViewAdapter(
        private val imageAddresses: List<String>
) :
        RecyclerView.Adapter<BaseViewHolder<String>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        return SmallProfileIconViewHolder(RecyclerItemChatmessageViewedUserIconBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<String>, position: Int) {
        holder.bindData(imageAddresses[position])
    }

    override fun getItemCount() = imageAddresses.size

}
