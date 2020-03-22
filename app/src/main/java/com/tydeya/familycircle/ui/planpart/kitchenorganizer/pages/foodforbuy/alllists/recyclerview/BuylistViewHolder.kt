package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyList
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import kotlinx.android.synthetic.main.buylist_cardview.view.*
import java.util.*

class BuylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(buyList: BuyList) {
        itemView.buylist_card_title.text = buyList.title
        val calendar = GregorianCalendar()
        calendar.timeInMillis = buyList.dateOfCreate.time
        itemView.buylist_card_date.text = DateRefactoring.getDateLocaleText(calendar)
    }

}