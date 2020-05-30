package com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.family.FamilyDTO
import com.tydeya.familycircle.databinding.RecyclerItemFamilySelectionBinding

class FamilyViewHolder(
        private val clickListener: SelectFamilyRecyclerViewClickListener,
        private val binding: RecyclerItemFamilySelectionBinding
) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(family: FamilyDTO) {
        binding.title.text = family.title
        binding.numberOfMembers.text = family.nMembers.toString()
        binding.root.setOnClickListener { clickListener.familySelected(family.id) }
    }
}
