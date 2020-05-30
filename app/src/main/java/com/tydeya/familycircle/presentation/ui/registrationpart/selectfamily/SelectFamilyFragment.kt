package com.tydeya.familycircle.presentation.ui.registrationpart.selectfamily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.databinding.FragmentSelectFamilyBinding
import com.tydeya.familycircle.presentation.ui.registrationpart.selectfamily.recyclerview.SelectFamilyRecyclerViewAdapter
import com.tydeya.familycircle.presentation.viewmodel.SelectableFamilySelectionViewModel
import com.tydeya.familycircle.utils.Resource

class SelectFamilyFragment : Fragment() {

    private var _binding: FragmentSelectFamilyBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SelectableFamilySelectionViewModel

    private lateinit var adapter: SelectFamilyRecyclerViewAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectFamilyBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SelectableFamilySelectionViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = SelectFamilyRecyclerViewAdapter(ArrayList())
        binding.familiesRecyclerview.adapter = adapter
        binding.familiesRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)

        viewModel.familiesLiveData.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                adapter.refreshData(it.data)
            } else {
                adapter.refreshData(ArrayList())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
