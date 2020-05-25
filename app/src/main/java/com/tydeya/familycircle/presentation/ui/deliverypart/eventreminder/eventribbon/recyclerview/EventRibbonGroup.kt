package com.tydeya.familycircle.presentation.ui.deliverypart.eventreminder.eventribbon.recyclerview

import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.RecyclerItemEventRibbonGroupHeaderBinding
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.Section


class EventRibbonGroup(private val groupTitle: String): Item<GroupieViewHolder>(), Group {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val binding = RecyclerItemEventRibbonGroupHeaderBinding.bind(viewHolder.itemView)
        binding.eventRibbonGroupTitle.text = groupTitle
    }

    override fun getLayout() = R.layout.recycler_item_event_ribbon_group_header
}