package com.tydeya.familycircle.presentation.ui.registrationpart.selectfamily.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.family.FamilyDTO
import com.tydeya.familycircle.databinding.RecyclerItemFamilySelectionBinding

class SelectFamilyRecyclerViewAdapter(
        private val clickListener: SelectFamilyRecyclerViewClickListener,
        private val families: ArrayList<FamilyDTO>
) :
        RecyclerView.Adapter<FamilyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        return FamilyViewHolder(clickListener, RecyclerItemFamilySelectionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        holder.bindData(families[position])
    }

    fun refreshData(families: List<FamilyDTO>) {
        val diffResult = DiffUtil
                .calculateDiff(FamilySelectionRecyclerDiffUtil(this.families, families))

        this.families.clear()
        this.families.addAll(families)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = families.size

}