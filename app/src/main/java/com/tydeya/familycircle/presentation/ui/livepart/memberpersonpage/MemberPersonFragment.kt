package com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.NavigateConsts
import com.tydeya.familycircle.data.familymember.FamilyMemberDto
import com.tydeya.familycircle.databinding.FragmentFamilyMemberViewBinding
import com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.twitterrecycler.TwitterRecyclerViewAdapter
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import com.tydeya.familycircle.utils.extensions.getUserPhone
import com.tydeya.familycircle.utils.extensions.popBackStack
import com.tydeya.familycircle.utils.extensions.toArrayList
import com.tydeya.familycircle.utils.getDp

class MemberPersonFragment : Fragment() {

    private var _binding: FragmentFamilyMemberViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var familyViewModel: FamilyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFamilyMemberViewBinding.inflate(inflater, container, false)
        familyViewModel = ViewModelProviders
                .of(requireActivity(), FamilyViewModelFactory(requireActivity().currentFamilyId))
                .get(FamilyViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments().getString(NavigateConsts.BUNDLE_FULL_PHONE_NUMBER)?.let {
            initFamilyView(it)
            initTweetRibbon(it)
        } ?: popBackStack()

        binding.toolbar.setNavigationOnClickListener {
            popBackStack()
        }
        binding.familyMemberViewAddTweet.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.addTweetFragment)
        }
    }

    private fun initTweetRibbon(phone: String) {
        val twitterAdapter = TwitterRecyclerViewAdapter()
        binding.twitterRecycler.adapter = twitterAdapter
        binding.twitterRecycler.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        familyViewModel.tweets.observe(viewLifecycleOwner, Observer { tweets ->
            when (tweets) {
                is Resource.Success -> twitterAdapter.refreshTweets(
                        tweets.data
                                .filter { it.authorPhone == phone }
                                .toArrayList()
                )
                is Resource.Loading -> twitterAdapter.refreshTweets(ArrayList())
                is Resource.Failure -> popBackStack()
            }
        })
    }

    private fun initFamilyView(userPhoneNumber: String) {
        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    val memberResource = familyViewModel.getFamilyMemberByNumber(userPhoneNumber)
                    if (memberResource is Resource.Success) {
                        setCurrentData(FamilyMemberDto(memberResource.data))
                        setManagerMode(memberResource.data.fullPhoneNumber == getUserPhone())
                    } else {
                        popBackStack()
                    }
                }
                is Resource.Failure -> popBackStack()

            }
        })
    }

    private fun setCurrentData(dto: FamilyMemberDto) {
        binding.familyMemberViewNameText.text = dto.name
        if (dto.imageAddress != "") {
            binding.familyViewPhoto.setPadding(0, 0, 0, 0)
            Glide.with(requireContext()).load(dto.imageAddress).into(binding.familyViewPhoto)
        } else {
            val dpForPadding = getDp(requireContext(), 20)
            binding.familyViewPhoto.setPadding(dpForPadding, dpForPadding, dpForPadding, dpForPadding)
        }
        if (dto.birthDate == "") {
            binding.familyMemberViewBirthdateText.text = resources
                    .getString(R.string.family_member_view_date_of_birth_not_known)
            binding.familyMemberViewZodiacSign.visibility = View.GONE
        } else {
            binding.familyMemberViewBirthdateText.text = dto.birthDate
            binding.familyMemberViewZodiacSign.text = dto.zodiacSign
            binding.familyMemberViewZodiacSign.visibility = View.VISIBLE
        }
        if (dto.studyPlace == "") {
            binding.familyMemberViewStudyPlace.visibility = View.GONE
        } else {
            binding.familyMemberViewStudyPlace.visibility = View.VISIBLE
            binding.familyMemberViewStudyPlace.text = dto.studyPlace
        }
        if (dto.workPlace == "") {
            binding.familyMemberViewWorkPlace.visibility = View.GONE
        } else {
            binding.familyMemberViewWorkPlace.visibility = View.VISIBLE
            binding.familyMemberViewWorkPlace.text = dto.workPlace
        }

        if (dto.isOnline) {
            binding.familyMemberViewOnlineText.text = requireContext().getString(R.string.online_text);
            binding.familyMemberViewOnlineText.setBackgroundColor(resources.getColor(R.color.colorOnlineGreen));
        } else {
            binding.familyMemberViewOnlineText.text = requireContext().getString(R.string.offline_text);
            binding.familyMemberViewOnlineText.setBackgroundColor(resources.getColor(R.color.colorGray));
        }
    }

    private fun setManagerMode(managerMode: Boolean) {
        if (managerMode) {
            binding.familyMemberViewSettings.visibility = View.VISIBLE
            binding.familyMemberViewSettings.isEnabled = true
            binding.familyMemberViewSettings.setOnClickListener { v: View -> showPopUpSettingsMenu(v) }
            binding.familyMemberViewAddTweet.visibility = View.VISIBLE
        } else {
            binding.familyMemberViewSettings.visibility = View.INVISIBLE
            binding.familyMemberViewAddTweet.visibility = View.INVISIBLE
            binding.familyMemberViewSettings.isEnabled = false
        }
    }

    private fun showPopUpSettingsMenu(v: View) {
        val popupMenu = PopupMenu(context, v)
        val menuInflater = popupMenu.menuInflater
        menuInflater.inflate(R.menu.settings_person_page_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.edit_person_page -> {
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.memberPersonEditFragment)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
