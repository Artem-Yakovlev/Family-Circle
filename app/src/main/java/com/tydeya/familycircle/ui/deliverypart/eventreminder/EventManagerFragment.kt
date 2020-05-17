package com.tydeya.familycircle.ui.deliverypart.eventreminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.FragmentEventReminderBinding
import com.tydeya.familycircle.ui.deliverypart.eventreminder.adapter.EventReminderPagerAdapter


class EventManagerFragment(

) :
        Fragment(R.layout.fragment_event_reminder) {

    private var _binding: FragmentEventReminderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentEventReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = EventReminderPagerAdapter(this)
        binding.eventReminderPager.adapter = pagerAdapter

        TabLayoutMediator(binding.eventReminderTabLayout, binding.eventReminderPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.event_ribbon_title)
                1 -> getString(R.string.kitchen_organizer_food_in_fridge_title)
                else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
