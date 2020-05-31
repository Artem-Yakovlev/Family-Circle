package com.tydeya.familycircle.presentation.ui.registrationpart.familyselection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.leinardi.android.speeddial.SpeedDialView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.Application.CURRENT_FAMILY_ID
import com.tydeya.familycircle.data.constants.Application.SHARED_PREFERENCE_USER_SETTINGS
import com.tydeya.familycircle.databinding.FragmentSelectFamilyBinding
import com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.jointofamilyviacode.JoinToFamilyViaCodeDialog
import com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.recyclerview.SelectFamilyRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.recyclerview.SelectFamilyRecyclerViewClickListener
import com.tydeya.familycircle.presentation.viewmodel.FamilySelectionViewModel
import com.tydeya.familycircle.utils.Resource

class SelectFamilyFragment : Fragment(), SelectFamilyRecyclerViewClickListener {

    private var _binding: FragmentSelectFamilyBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FamilySelectionViewModel

    private lateinit var adapter: SelectFamilyRecyclerViewAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectFamilyBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(FamilySelectionViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initFloatingButton()
    }

    private fun initRecyclerView() {
        adapter = SelectFamilyRecyclerViewAdapter(this, ArrayList())
        binding.familiesRecyclerview.adapter = adapter
        binding.familiesRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)

        viewModel.families.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                adapter.refreshData(it.data)
            } else {
                adapter.refreshData(ArrayList())
            }
        })
    }

    private fun initFloatingButton() {
        binding.floatingButton.inflate(R.menu.add_new_family_menu)

        binding.floatingButton
                .setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
                    when (actionItem.id) {
                        R.id.join_via_invite_code -> {
                            JoinToFamilyViaCodeDialog.newInstance()
                                    .show(parentFragmentManager, JoinToFamilyViaCodeDialog.TAG)
                            binding.floatingButton.close()
                            return@OnActionSelectedListener true
                        }
                        R.id.create_new_family -> {
                            CreateNewFamilyDialogFragment.newInstance()
                                    .show(parentFragmentManager, CreateNewFamilyDialogFragment.TAG)
                            binding.floatingButton.close()
                            return@OnActionSelectedListener true
                        }
                    }
                    return@OnActionSelectedListener false
                })
    }

    override fun familySelected(familyId: String) {
        requireActivity().getSharedPreferences(SHARED_PREFERENCE_USER_SETTINGS,
                Context.MODE_PRIVATE).edit().putString(CURRENT_FAMILY_ID, familyId).apply()

        NavHostFragment.findNavController(this).navigate(R.id.welcomeFamilyFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
