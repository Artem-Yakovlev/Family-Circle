package com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.jointofamilyviacode.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.databinding.RecyclerItemFamilyInviteCodeBinding

class FamilyInvitesRecyclerViewAdapter(
        private val inviteCodes: ArrayList<String>,
        private val listener: FamilyInviteCodeOnClickListener
) : RecyclerView.Adapter<FamilyInviteCodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FamilyInviteCodeViewHolder(
                    RecyclerItemFamilyInviteCodeBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false),
                    listener
            )


    override fun getItemCount() = inviteCodes.size

    override fun onBindViewHolder(holder: FamilyInviteCodeViewHolder, position: Int) {
        holder.bindData(inviteCodes[position])
    }

    fun refreshData(inviteCodes: ArrayList<String>) {
        val diffResult = DiffUtil.calculateDiff(
                FamilyInvitesDiffUtilCallback(this.inviteCodes, inviteCodes)
        )

        this.inviteCodes.clear()
        this.inviteCodes.addAll(inviteCodes)

        diffResult.dispatchUpdatesTo(this)
    }
}