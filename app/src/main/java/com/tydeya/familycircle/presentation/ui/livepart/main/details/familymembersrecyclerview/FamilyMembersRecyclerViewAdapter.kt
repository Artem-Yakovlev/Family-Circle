package com.tydeya.familycircle.presentation.ui.livepart.main.details.familymembersrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.familymember.FamilyMember
import java.util.*

class FamilyMembersRecyclerViewAdapter(
        private var members: ArrayList<FamilyMember>,
        private val onClickMemberStoryListener: OnClickMemberStoryListener
) :
        RecyclerView.Adapter<FamilyMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyMemberViewHolder {
        return FamilyMemberViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_family_member_live_page, parent, false),
                onClickMemberStoryListener)
    }

    override fun onBindViewHolder(holder: FamilyMemberViewHolder, position: Int) {
        holder.bindData(members[position])
    }

    override fun getItemCount() = members.size

    fun refreshData(members: ArrayList<FamilyMember>) {
        val diffResult = DiffUtil.calculateDiff(FamilyMembersRecyclerDiffUtil(this.members, members))

        this.members.clear()
        this.members.addAll(members)

        diffResult.dispatchUpdatesTo(this)
    }

}