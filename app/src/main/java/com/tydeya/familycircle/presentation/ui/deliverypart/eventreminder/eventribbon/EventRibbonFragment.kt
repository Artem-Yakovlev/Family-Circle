package com.tydeya.familycircle.presentation.ui.deliverypart.eventreminder.eventribbon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.FragmentEventRibbonBinding
import com.tydeya.familycircle.presentation.MainActivity
import com.tydeya.familycircle.presentation.ui.deliverypart.eventreminder.eventribbon.recyclerview.DailySection
import com.tydeya.familycircle.presentation.viewmodel.EventReminderViewModel
import com.tydeya.familycircle.utils.Resource
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class EventRibbonFragment : Fragment() {

    private var _binding: FragmentEventRibbonBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EventReminderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentEventRibbonBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(EventReminderViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        initRecyclerView()
        initFloatingButton()
    }

    private fun initRecyclerView() {
        val groupieAdapter = GroupAdapter<GroupieViewHolder>()

        viewModel.eventsResource.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it is Resource.Success) {
                groupieAdapter.add(DailySection(it.data))
            }
        })

        with(binding.eventsRibbonMainRecyclerview) {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,
                    false)
        }
    }

    private fun initFloatingButton() {
        binding.eventReminderFloatingButtonAddEvent
                .attachToRecyclerView(binding.eventsRibbonMainRecyclerview)

        binding.eventReminderFloatingButtonAddEvent.setOnClickListener {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_eventRibbonFragment_to_createNewFamilyEventFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
