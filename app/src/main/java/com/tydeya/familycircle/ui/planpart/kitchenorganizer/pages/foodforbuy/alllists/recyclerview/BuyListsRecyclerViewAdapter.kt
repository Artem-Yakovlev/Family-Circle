package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyList
import kotlinx.android.synthetic.main.buylist_cardview.view.*

class BuyListsRecyclerViewAdapter(val context: Context, var buyLists: List<BuyList>,
                                  var onClickListener: OnBuyListClickListener) :
        RecyclerView.Adapter<BuylistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuylistViewHolder =
            BuylistViewHolder(LayoutInflater.from(context).inflate(R.layout.buylist_cardview,
                    parent, false), onClickListener)

    override fun getItemCount(): Int = buyLists.size

    override fun onBindViewHolder(holder: BuylistViewHolder, position: Int) {
        holder.bindData(buyLists[position], position)
    }
}