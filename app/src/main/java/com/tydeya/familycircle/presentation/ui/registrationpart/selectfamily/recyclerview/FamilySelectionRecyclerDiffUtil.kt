package com.tydeya.familycircle.presentation.ui.registrationpart.selectfamily.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.family.FamilyDTO

class FamilySelectionRecyclerDiffUtil(
        private val oldList: List<FamilyDTO>,
        private val newList: List<FamilyDTO>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}