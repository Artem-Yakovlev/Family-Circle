package com.tydeya.familycircle.presentation.ui.livepart.main.details

import android.content.Context
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
import com.tydeya.familycircle.data.constants.Application
import com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_FULL_PHONE_NUMBER
import com.tydeya.familycircle.databinding.FragmentMainLivePageBinding
import com.tydeya.familycircle.domain.cooperationorganizer.interactor.abstraction.CooperationInteractorCallback
import com.tydeya.familycircle.domain.oldfamilyinteractor.abstraction.FamilyInteractorCallback
import com.tydeya.familycircle.presentation.ui.livepart.main.details.familymembersrecyclerview.FamilyMembersRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.livepart.main.details.familymembersrecyclerview.OnClickMemberStoryListener
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.Resource
import java.util.*

class MainLiveFragment : Fragment(), OnClickMemberStoryListener, CooperationInteractorCallback {

    private var _binding: FragmentMainLivePageBinding? = null
    private val binding get() = _binding!!

    private lateinit var familyRecyclerViewAdapter: FamilyMembersRecyclerViewAdapter

    private lateinit var familyViewModelFactory: FamilyViewModelFactory
    private lateinit var familyViewModel: FamilyViewModel

//    @Inject
//    var cooperationInteractor: CooperationInteractor? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainLivePageBinding.inflate(inflater, container, false)

        val familyId = requireActivity()
                .getSharedPreferences(Application.SHARED_PREFERENCE_USER_SETTINGS, Context.MODE_PRIVATE)
                .getString(Application.CURRENT_FAMILY_ID, "")!!

        familyViewModelFactory = FamilyViewModelFactory(familyId)
        familyViewModel = ViewModelProviders.of(requireActivity(), familyViewModelFactory)
                .get(FamilyViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFamilyStoriesRecyclerView()
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

//    private fun initCooperationRecyclerView() {
//        cooperationRecyclerViewAdapter = CooperationRecyclerViewAdapter(requireContext(), ArrayList())
//        binding.mainLivePageLiveTapeRecyclerview.adapter = cooperationRecyclerViewAdapter
//        binding.mainLivePageLiveTapeRecyclerview.layoutManager = LinearLayoutManager(
//                context, LinearLayoutManager.VERTICAL, false)
//    }

    override fun onClickFamilyMember(phoneNumber: String) {
        NavHostFragment.findNavController(this).navigate(
                R.id.familyMemberViewFragment,
                bundleOf(BUNDLE_FULL_PHONE_NUMBER to phoneNumber)
        )
    }

    override fun cooperationDataFromServerUpdated() {
//        cooperationRecyclerViewAdapter!!.refreshData(cooperationInteractor!!.cooperationData)
    }

    override fun onPause() {
        super.onPause()
//        cooperationInteractor!!.unsubscribe(this)
    }

    override fun onResume() {
        super.onResume()
//        cooperationInteractor!!.subscribe(this)
    }
}