package com.tydeya.familycircle.presentation.ui.registrationpart.selectfamily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tydeya.familycircle.databinding.FragmentSelectFamilyBinding
import com.tydeya.familycircle.presentation.viewmodel.FamilySelectionViewModel

class SelectFamilyFragment : Fragment() {

    private var _binding: FragmentSelectFamilyBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSelectFamilyBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(FamilySelectionViewModel::class.java)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}