package com.tydeya.familycircle.presentation.ui.deliverypart.eventreminder.recyclerview

import android.view.View
import androidx.core.content.ContextCompat
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.eventreminder.EventStyleTheme
import com.tydeya.familycircle.data.eventreminder.EventTimeType
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.databinding.CardviewEventRibbonItemBinding
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class EventRibbonItem(private val eventData: FamilyEvent) : Item<GroupieViewHolder>() {

    private lateinit var binding: CardviewEventRibbonItemBinding

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        binding = CardviewEventRibbonItemBinding.bind(viewHolder.itemView)
        initInformation()
        initStyle()
    }

    private fun initInformation() {
        binding.eventTitle.text = eventData.information.title
        binding.eventTitle.isSelected = true

        binding.eventDescription.text = eventData.information.description
        binding.eventDescription.visibility = if (eventData.information.description == "") {
            View.GONE
        } else {
            View.VISIBLE
        }

        binding.eventTime.visibility = if (eventData.time.timeType == EventTimeType.ONLY_DATE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun initStyle() {
        val color = when (eventData.style.theme) {
            EventStyleTheme.COLOR_DARK_BLUE -> R.color.colorEventDarkBlue
            EventStyleTheme.COLOR_LIGHT_BLUE -> R.color.colorEventLightBlue
            EventStyleTheme.COLOR_DARK_GREEN -> R.color.colorEventDarkGreen
            EventStyleTheme.COLOR_LIGHT_GREEN -> R.color.colorEventLightGreen
            EventStyleTheme.COLOR_ORANGE -> R.color.colorEventOrange
            else -> R.color.colorEventLightBlue
        }

        binding.eventCardBackground.setBackgroundColor(
                ContextCompat.getColor(binding.root.context, color)
        )
    }

    override fun getLayout() = R.layout.cardview_event_ribbon_item

}