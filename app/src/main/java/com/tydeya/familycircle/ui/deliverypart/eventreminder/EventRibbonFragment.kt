package com.tydeya.familycircle.ui.deliverypart.eventreminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.data.eventreminder.EventType
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.databinding.FragmentEventRibbonBinding
import com.tydeya.familycircle.ui.deliverypart.eventreminder.recyclerview.DailySection
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.*
import kotlin.collections.ArrayList

class EventRibbonFragment : Fragment() {

    private var _binding: FragmentEventRibbonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentEventRibbonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventsList = ArrayList<FamilyEvent>().apply {
            add(FamilyEvent(GregorianCalendar(), "День пива", EventType.IMPORTANT))
            add(FamilyEvent(GregorianCalendar(), "День рождения Семена", EventType.BIRTHDATE))
            add(FamilyEvent(GregorianCalendar(), "Ежедневная пробежка", EventType.ROUTINE))
        }

        initRecyclerView(eventsList)
    }

    private fun initRecyclerView(eventItems: List<FamilyEvent>) {
        val groupieAdapter = GroupAdapter<GroupieViewHolder>()

        groupieAdapter.add(DailySection(eventItems))

        with(binding.eventsRibbonMainRecyclerview) {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,
                    false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
