package com.tydeya.familycircle.presentation.ui.livepart.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_FULL_PHONE_NUMBER
import com.tydeya.familycircle.databinding.FragmentMainLivePageBinding
import com.tydeya.familycircle.presentation.ui.livepart.main.details.familymembersrecyclerview.FamilyMembersRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.livepart.main.details.familymembersrecyclerview.OnClickMemberStoryListener
import com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.twitterrecycler.TwitterRecyclerViewAdapter
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId

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
        initTwitterRecycler()
        initPieChart()
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

    private fun initTwitterRecycler() {
        val twitterAdapter = TwitterRecyclerViewAdapter()
        binding.twitterRecycler.adapter = twitterAdapter
        binding.twitterRecycler.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
        )
        familyViewModel.tweets.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> twitterAdapter.refreshTweets(it.data)
                is Resource.Loading -> twitterAdapter.refreshTweets(ArrayList())
            }
        })

        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> twitterAdapter.refreshFamilyMembers(it.data)
                is Resource.Loading -> twitterAdapter.refreshFamilyMembers(ArrayList())
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

    private fun initPieChart() {

        val kitchenData = ArrayList<PieEntry>()
        kitchenData.add(PieEntry(16f, "Fresh food"))
        kitchenData.add(PieEntry(5f, "Ordinary food"))
        kitchenData.add(PieEntry(3f, "Spoiled food"))
        kitchenData.add(PieEntry(10f, "Unknown"))

        val dataSet = PieDataSet(kitchenData, "")

        val data = PieData(dataSet)
        data.setValueTextColor(getColor(requireContext(), R.color.colorWhite))
        data.setValueTextSize(16f)
        binding.kitchenPieChart.data = data
        dataSet.colors = listOf(
                getColor(requireContext(), R.color.colorFreshFood),
                getColor(requireContext(), R.color.colorOrdinaryFood),
                getColor(requireContext(), R.color.colorSpoiledFood),
                getColor(requireContext(), R.color.colorGray))
        binding.kitchenPieChart.animateXY(500, 500)

        binding.kitchenPieChart.legend.isEnabled = true
        binding.kitchenPieChart.centerText = "Shelf life products"
        binding.kitchenPieChart.setDrawEntryLabels(false)
        binding.kitchenPieChart.description.isEnabled = false
    }


    override fun onClickFamilyMember(phoneNumber: String) {
        NavHostFragment.findNavController(this).navigate(
                R.id.familyMemberViewFragment,
                bundleOf(BUNDLE_FULL_PHONE_NUMBER to phoneNumber)
        )
    }

}