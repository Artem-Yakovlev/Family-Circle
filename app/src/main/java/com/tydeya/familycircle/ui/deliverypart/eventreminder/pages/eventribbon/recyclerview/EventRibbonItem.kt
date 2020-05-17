package com.tydeya.familycircle.ui.deliverypart.eventreminder.pages.eventribbon.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.databinding.CardviewEventRibbonItemBinding
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class EventRibbonItem(private val eventData: FamilyEvent): Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val view = CardviewEventRibbonItemBinding.bind(viewHolder.itemView)
        view.eventTitle.text = eventData.title
    }

    override fun getLayout() = R.layout.cardview_event_ribbon_item

}