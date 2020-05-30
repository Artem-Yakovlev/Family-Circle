package com.tydeya.familycircle.presentation.ui.registrationpart.familyselection

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.FragmentWelcomeFamilyBinding
import com.tydeya.familycircle.presentation.MainActivity


class WelcomeFamilyFragment : Fragment() {

    private var _binding: FragmentWelcomeFamilyBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentWelcomeFamilyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.welcomeNextButton.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

}
