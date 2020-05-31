package com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.jointofamilyviacode.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.databinding.RecyclerItemFamilyInviteCodeBinding

class FamilyInviteCodeViewHolder(
        private val binding: RecyclerItemFamilyInviteCodeBinding,
        private val listener: FamilyInviteCodeOnClickListener
) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(inviteCode: String) {
        binding.inviteCode.text = inviteCode

        binding.acceptCodeButton.setOnClickListener {
            listener.acceptCode(inviteCode)
        }

        binding.refuseCodeButton.setOnClickListener {
            listener.refuseCode(inviteCode)
        }
    }

}

interface FamilyInviteCodeOnClickListener {

    fun acceptCode(inviteCode: String)

    fun refuseCode(inviteCode: String)
}