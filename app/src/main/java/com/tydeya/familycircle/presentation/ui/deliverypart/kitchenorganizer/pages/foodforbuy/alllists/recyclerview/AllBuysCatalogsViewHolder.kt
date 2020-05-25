package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.databinding.CardviewBuylistBinding
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import java.util.*

class AllBuysCatalogsViewHolder(private val binding: CardviewBuylistBinding,
                                private val clickListener: OnBuyCatalogClickListener)
    : RecyclerView.ViewHolder(binding.root) {

    fun bindData(buyCatalog: BuyCatalog, position: Int) {
        binding.buylistCardTitle.text = buyCatalog.title

        val calendar = GregorianCalendar()
        calendar.timeInMillis = buyCatalog.dateOfCreate.time

        binding.buylistCardDate.text = DateRefactoring.getDateLocaleText(calendar)
        binding.root.setOnClickListener { clickListener.onBuyCatalogClick(position) }

        binding.numberOfProducts.text = itemView.context
                .getString(R.string.kitchen_organizer_number_of_products,
                        buyCatalog.nPurchased.toString(), buyCatalog.nProducts.toString())
    }

}