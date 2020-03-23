package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import kotlinx.android.synthetic.main.buylist_cardview.view.*
import java.util.*

class BuyCatalogViewHolder(itemView: View, var clickListener: OnBuyCatalogClickListener)
    : RecyclerView.ViewHolder(itemView) {

    fun bindData(buyCatalog: BuyCatalog, position: Int) {
        itemView.buylist_card_title.text = buyCatalog.title
        val calendar = GregorianCalendar()
        calendar.timeInMillis = buyCatalog.dateOfCreate.time
        itemView.buylist_card_date.text = DateRefactoring.getDateLocaleText(calendar)
        itemView.setOnClickListener{clickListener.onBuyCatalogClick(position)}
    }

}