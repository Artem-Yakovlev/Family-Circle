package com.tydeya.familycircle.presentation.ui.livepart.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_FULL_PHONE_NUMBER
import com.tydeya.familycircle.databinding.FragmentMainLivePageBinding
import com.tydeya.familycircle.presentation.ui.livepart.main.details.familymembersrecyclerview.FamilyMembersRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.livepart.main.details.familymembersrecyclerview.OnClickMemberStoryListener
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import java.util.*

class MainLiveFragment : Fragment(), OnClickMemberStoryListener {

    private var _binding: FragmentMainLivePageBinding? = null
    private val binding get() = _binding!!

    private lateinit var familyRecyclerViewAdapter: FamilyMembersRecyclerViewAdapter

    private lateinit var familyViewModel: FamilyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainLivePageBinding.inflate(inflater, container, false)

        familyViewModel = ViewModelProviders
                .of(requireActivity(), FamilyViewModelFactory(requireActivity().currentFamilyId))
                .get(FamilyViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFamilyStoriesRecyclerView()
        initToolbar()
    }

    private fun initFamilyStoriesRecyclerView() {
        familyRecyclerViewAdapter = FamilyMembersRecyclerViewAdapter(
                ArrayList(), this
        )
        binding.mainLivePageFamilyRecyclerview.adapter = familyRecyclerViewAdapter
        binding.mainLivePageFamilyRecyclerview.layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.HORIZONTAL, false)

        familyViewModel.familyMembers.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    familyRecyclerViewAdapter.refreshData(it.data)
                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {

                }
            }
        })
    }

    private fun initToolbar() {
        familyViewModel.familyData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    binding.toolbar.title = it.data.title
                }

                is Resource.Loading -> {
                    binding.toolbar.title = getString(R.string.loading)
                }
            }
        })
    }

    override fun onClickFamilyMember(phoneNumber: String) {
        NavHostFragment.findNavController(this).navigate(
                R.id.familyMemberViewFragment,
                bundleOf(BUNDLE_FULL_PHONE_NUMBER to phoneNumber)
        )
    }

}