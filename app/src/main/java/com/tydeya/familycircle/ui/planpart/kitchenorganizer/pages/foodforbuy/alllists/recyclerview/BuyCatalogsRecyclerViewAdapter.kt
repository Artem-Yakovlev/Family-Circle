package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog

class BuyCatalogsRecyclerViewAdapter(val context: Context, var buyCatalogs: List<BuyCatalog>,
                                     var onClickListener: OnBuyCatalogClickListener) :
        RecyclerView.Adapter<BuyCatalogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyCatalogViewHolder =
            BuyCatalogViewHolder(LayoutInflater.from(context).inflate(R.layout.buylist_cardview,
                    parent, false), onClickListener)

    override fun getItemCount(): Int = buyCatalogs.size

    override fun onBindViewHolder(holder: BuyCatalogViewHolder, position: Int) {
        holder.bindData(buyCatalogs[position], position)
    }
}