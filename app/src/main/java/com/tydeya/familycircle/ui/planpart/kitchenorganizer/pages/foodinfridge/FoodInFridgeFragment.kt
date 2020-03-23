package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.recyclerview.FoodInFridgeRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_food_in_fridge.*


class FoodInFridgeFragment : Fragment(R.layout.fragment_food_in_fridge) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foods = ArrayList<Food>()
        foods.add(Food("Пиво", "Это жизнь",  FoodStatus.IN_FRIDGE, .0, .0, .0))
        foods.add(Food("Водка", "Это вторая жизнь", FoodStatus.IN_FRIDGE, .0, .0, .0))
        foods.add(Food("Закуска", "Это жизнь",  FoodStatus.IN_FRIDGE, .0, .0, .0))

        val adapter = FoodInFridgeRecyclerViewAdapter(context!!, foods)
        food_in_fridge_recyclerview.adapter = adapter
        food_in_fridge_recyclerview.layoutManager =
                LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
    }

}
