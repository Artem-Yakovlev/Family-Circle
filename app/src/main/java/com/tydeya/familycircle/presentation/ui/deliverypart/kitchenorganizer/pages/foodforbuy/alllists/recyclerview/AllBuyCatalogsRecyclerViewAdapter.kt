package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.alllists.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.databinding.CardviewBuylistBinding

class AllBuyCatalogsRecyclerViewAdapter(var buyCatalogs: ArrayList<BuyCatalog>,
                                        var onClickListener: OnBuyCatalogClickListener) :
        RecyclerView.Adapter<AllBuysCatalogsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBuysCatalogsViewHolder =
            AllBuysCatalogsViewHolder(CardviewBuylistBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false), onClickListener)

    override fun getItemCount(): Int = buyCatalogs.size

    override fun onBindViewHolder(holderAll: AllBuysCatalogsViewHolder, position: Int) {
        holderAll.bindData(buyCatalogs[position], position)
    }

    fun refreshData(buyCatalogs: ArrayList<BuyCatalog>) {
        val diffResult = DiffUtil.calculateDiff(AllBuyCatalogsDiffUtilCallback(this.buyCatalogs, buyCatalogs))

        this.buyCatalogs.clear()
        this.buyCatalogs.addAll(buyCatalogs)

        diffResult.dispatchUpdatesTo(this)
    }
}