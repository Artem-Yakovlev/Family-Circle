package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodinfridge.recyclerview

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.CardviewFoodInFridgeBinding
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import java.util.*

const val dayInMillis = 86_400_000

class FoodInFridgeViewHolder(private val binding: CardviewFoodInFridgeBinding,
                             private val listener: FoodInFridgeViewHolderClickListener) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(food: Food) {
        initFoodTitle(food)
    }

    private fun initFoodTitle(food: Food) {
        val measure = itemView.context.resources
                .getStringArray(R.array.quantitative_type_of_product)[food.measureType.ordinal]

        if (food.measureType.ordinal != 0) {
            binding.foodInFridgeTitle.text = itemView.context.getString(
                    R.string.kitchen_organizer_buys_catalog_food_title_and_measure_placeholder,
                    food.title, food.quantityOfMeasure.toString(), measure)
        } else {
            binding.foodInFridgeTitle.text = food.title
        }
        binding.foodInFridgeTitle.isSelected = true

        binding.editFoodButton.setOnClickListener {
            listener.onFoodInFridgeVhEditClick(food)
        }

        binding.eatFoodButton.setOnClickListener {
            listener.onFoodInFridgeVhEatClick(food)
        }
        shelfLifeProcessing(food)
    }

    private fun shelfLifeProcessing(food: Food) {
        var shelfLifeDays = 0L

        if (food.shelfLifeTimeStamp == -1L) {

            binding.foodInFridgeShelfLife.visibility = View.GONE
            setEatButtonColor(R.color.colorGray)

        } else {

            binding.foodInFridgeShelfLife.visibility = View.VISIBLE
            setEatButtonColor(R.color.colorFreshFood)

            val calendar = GregorianCalendar().apply { timeInMillis = food.shelfLifeTimeStamp }
            shelfLifeDays = (food.shelfLifeTimeStamp - GregorianCalendar().timeInMillis) /
                    (dayInMillis)

            binding.foodInFridgeShelfLife.text = itemView.context.getString(
                    R.string.food_in_fridge_shelf_life_placeholder,
                    DateRefactoring.getDateLocaleText(calendar),
                    if (shelfLifeDays == 0L) {
                        itemView.context.getString(R.string.food_in_fridge_shelf_life_tomorrow)
                    } else {
                        "$shelfLifeDays ${itemView.context
                                .getString(R.string.food_in_fridge_shelf_life_days)}"
                    })

        }

        when {
            food.shelfLifeTimeStamp == -1L -> {
                setEatButtonColor(R.color.colorGray)
            }
            shelfLifeDays < 0L -> {
                setEatButtonColor(R.color.colorSpoiledFood)
            }
            shelfLifeDays < 2L -> {
                setEatButtonColor(R.color.colorOrdinaryFood)
            }
            else -> {
                setEatButtonColor(R.color.colorFreshFood)
            }
        }
    }

    private fun setEatButtonColor(colorId: Int) {
        ImageViewCompat.setImageTintList(binding.eatFoodButton,
                ColorStateList.valueOf(ContextCompat.getColor(binding.eatFoodButton.context,
                        colorId)))
    }
}

interface FoodInFridgeViewHolderClickListener {

    fun onFoodInFridgeVhDeleteClick(productId: String)

    fun onFoodInFridgeVhEditClick(food: Food)

    fun onFoodInFridgeVhEatClick(food: Food)
}