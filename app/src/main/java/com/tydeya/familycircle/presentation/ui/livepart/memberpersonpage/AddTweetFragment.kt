package com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.FragmentAddTweetBinding
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import com.tydeya.familycircle.presentation.MainActivity
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.KeyboardHelper
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import com.tydeya.familycircle.utils.extensions.popBackStack
import com.tydeya.familycircle.utils.extensions.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTweetFragment : Fragment(R.layout.fragment_add_tweet) {

    private var _binding: FragmentAddTweetBinding? = null
    private val binding get() = _binding!!

    private lateinit var familyViewModel: FamilyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddTweetBinding.inflate(inflater, container, false)
        familyViewModel = ViewModelProviders
                .of(requireActivity(), FamilyViewModelFactory(requireActivity().currentFamilyId))
                .get(FamilyViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            popBackStack()
        }
        binding.tweetDone.setOnClickListener {
            if (!DataConfirming.isEmptyCheck(binding.dialogTweetInput, true)) {
                GlobalScope.launch(Dispatchers.Default) {
                    familyViewModel.addTweet(
                            requireContext().currentFamilyId,
                            binding.dialogTweetInput.value.trim()
                    )
                }
                KeyboardHelper.hideKeyboard(requireActivity())
                popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setBottomNavigationVisibility(false)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as MainActivity).setBottomNavigationVisibility(true)
    }

}
