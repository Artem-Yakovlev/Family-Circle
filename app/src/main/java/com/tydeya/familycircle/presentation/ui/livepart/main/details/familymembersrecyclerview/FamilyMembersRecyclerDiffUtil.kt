package com.tydeya.familycircle.presentation.ui.livepart.main.details.familymembersrecyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.familymember.FamilyMember

class FamilyMembersRecyclerDiffUtil(
        private val oldList: List<FamilyMember>,
        private val newList: List<FamilyMember>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].fullPhoneNumber == newList[newItemPosition].fullPhoneNumber

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}