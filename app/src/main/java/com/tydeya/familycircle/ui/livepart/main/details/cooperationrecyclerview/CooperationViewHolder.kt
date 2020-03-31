package com.tydeya.familycircle.ui.livepart.main.details.cooperationrecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.cooperation.CooperationType
import kotlinx.android.synthetic.main.cardview_cooperation.view.*

class CooperationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(type: CooperationType, name: String, item: String) {

        Glide.with(itemView.context).load(imageByType(type))
                .into(itemView.cardview_cooperation_image)

        itemView.cardview_cooperation_text.text =
                itemView.context.resources.getString(textPlaceHolderByType(type),
                        cutText(name), cutText(item))

    }

    private fun imageByType(type: CooperationType) = when (type) {
        CooperationType.ADD_PRODUCT, CooperationType.DROP_PRODUCT, CooperationType.EAT_PRODUCT
        -> R.drawable.ic_kitchen_black_60dp

        CooperationType.PERFORM_TASK, CooperationType.GIVE_TASK, CooperationType.REFUSE_TASK
        -> R.drawable.ic_planning_black_60dp
    }

    private fun textPlaceHolderByType(type: CooperationType) = when (type) {
        CooperationType.ADD_PRODUCT -> R.string.cooperation_add_product_placeholder
        CooperationType.EAT_PRODUCT -> R.string.cooperation_eat_product_placeholder
        CooperationType.DROP_PRODUCT -> R.string.cooperation_drop_product_placeholder
        CooperationType.GIVE_TASK -> R.string.cooperation_give_task_placeholder
        CooperationType.REFUSE_TASK -> R.string.cooperation_refuse_task_placeholder
        CooperationType.PERFORM_TASK -> R.string.cooperation_perform_task_placeholder
    }

    private fun cutText(text: String) = if (text.length > 9) {
        "${text.subSequence(0, 7)}.."
    } else {
        text
    }
}
