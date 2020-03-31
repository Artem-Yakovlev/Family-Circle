package com.tydeya.familycircle.ui.livepart.main.details.cooperationrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.cooperation.Cooperation

class CooperationRecyclerViewAdapter(
        val context: Context,
        var cooperationData: ArrayList<Cooperation>

) :
        RecyclerView.Adapter<CooperationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CooperationViewHolder(LayoutInflater
                    .from(context).inflate(R.layout.cardview_cooperation, parent, false))

    override fun getItemCount() = cooperationData.size

    override fun onBindViewHolder(holder: CooperationViewHolder, position: Int) {
        holder.bindData(cooperationData[position])
    }

    fun refreshData(cooperationData: ArrayList<Cooperation>) {
        this.cooperationData = cooperationData
        notifyDataSetChanged()
    }
}