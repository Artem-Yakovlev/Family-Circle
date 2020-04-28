package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.databinding.CardviewBuylistBinding
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import kotlinx.android.synthetic.main.cardview_buylist.view.*
import java.util.*

class AllBuyCatalogsViewHolder(private val binding: CardviewBuylistBinding,
                               private val clickListener: OnBuyCatalogClickListener)
    : RecyclerView.ViewHolder(binding.root) {

    fun bindData(buyCatalog: BuyCatalog, position: Int) {
        binding.buylistCardTitle.text = buyCatalog.title

        val calendar = GregorianCalendar()
        calendar.timeInMillis = buyCatalog.dateOfCreate.time

        binding.buylistCardDate.text = DateRefactoring.getDateLocaleText(calendar)
        binding.root.setOnClickListener { clickListener.onBuyCatalogClick(position) }
    }

}