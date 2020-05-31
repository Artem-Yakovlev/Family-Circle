package com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.jointofamilyviacode.recyclerview

import androidx.recyclerview.widget.DiffUtil

class FamilyInvitesDiffUtilCallback(
        private val oldList: List<String>,
        private val newList: List<String>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}