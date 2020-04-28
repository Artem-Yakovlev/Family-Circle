package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_ID
import com.tydeya.familycircle.databinding.FragmentFoodForBuyBinding
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.AllBuyCatalogsRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview.OnBuyCatalogClickListener
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.viewmodel.AllBuyCatalogsViewModel
import kotlinx.android.synthetic.main.fragment_food_for_buy.*

class FoodForBuyFragment : Fragment(), OnBuyCatalogClickListener {

    lateinit var adapter: AllBuyCatalogsRecyclerViewAdapter

    private var _binding: FragmentFoodForBuyBinding? = null
    private val binding get() = _binding!!

    private lateinit var allBuyCatalogsViewModel: AllBuyCatalogsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        App.getComponent().injectFoodForBuyFragment(this)

        _binding = FragmentFoodForBuyBinding.inflate(inflater, container, false)
        allBuyCatalogsViewModel = ViewModelProviders.of(this)
                .get(AllBuyCatalogsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initFloatingButton()
    }

    private fun initRecyclerView() {
        adapter = AllBuyCatalogsRecyclerViewAdapter(ArrayList(), this)
        binding.foodForBuyRecyclerview.setAdapter(adapter,
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false))
        food_for_buy_recyclerview.addVeiledItems(12)

        allBuyCatalogsViewModel.buyCatalogsResource.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.foodForBuyRecyclerview.veil()
                }
                is Resource.Success -> {
                    binding.foodForBuyRecyclerview.unVeil()
                    adapter.refreshData(it.data)
                }
                is Resource.Failure -> {

                }
            }
        })
    }

    private fun initFloatingButton() {
        floating_button.attachToRecyclerView(food_for_buy_recyclerview.getRecyclerView())
        floating_button.setOnClickListener {
            val newListDialog = CreateBuyListDialog()
            newListDialog.show(parentFragmentManager, "dialog_new_list")
        }
    }

    override fun onBuyCatalogClick(position: Int) {

        val allBuyCatalogsResourse = allBuyCatalogsViewModel.buyCatalogsResource.value

        if (allBuyCatalogsResourse is Resource.Success) {
            val navController = NavHostFragment.findNavController(this)
            val bundle = bundleOf((BUNDLE_ID to allBuyCatalogsResourse.data[position].id))
            navController.navigate(R.id.buyListFragment, bundle)
        }


    }
}
