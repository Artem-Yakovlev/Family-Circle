package com.tydeya.familycircle.presentation.ui.livepart.main.details.cooperationrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.cooperation.Cooperation
import com.tydeya.familycircle.data.cooperation.CooperationType
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.oldfamilyinteractor.details.FamilyInteractor
import javax.inject.Inject

class CooperationRecyclerViewAdapter(
        val context: Context,
        var cooperationData: ArrayList<Cooperation>

) :
        RecyclerView.Adapter<CooperationViewHolder>() {

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    init {
        App.getComponent().injectRecyclerViewAdapter(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CooperationViewHolder(LayoutInflater
                    .from(context).inflate(R.layout.cardview_cooperation, parent, false))

    override fun getItemCount() = cooperationData.size

    override fun onBindViewHolder(holder: CooperationViewHolder, position: Int) {
        val familyAssistant = FamilyAssistantImpl(familyInteractor.actualFamily)

        val type = cooperationData[position].type
        var name = context.resources.getString(R.string.unknown_text)
        var item = cooperationData[position].item

        familyAssistant.getUserByPhone(cooperationData[position].userPhone)?.let {
            name = it.description.name
        }

        when (type) {
            CooperationType.PERFORM_TASK, CooperationType.GIVE_TASK, CooperationType.REFUSE_TASK
            -> {
                item = if (familyAssistant.getUserByPhone(item) != null) {
                    familyAssistant.getUserByPhone(item).description.name
                } else {
                    context.resources.getString(R.string.unknown_text)
                }
            }
            else -> {
            }

        }

        holder.bindData(type, name, item)
    }

    fun refreshData(cooperationData: ArrayList<Cooperation>) {
        this.cooperationData = cooperationData
        notifyDataSetChanged()
    }
}