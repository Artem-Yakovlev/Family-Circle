package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.RecyclerItemTaskWorkerBinding

class WorkerViewHolder(
        private val binding: RecyclerItemTaskWorkerBinding
) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(workerName: String) {
        binding.root.text = workerName
        binding.root.setBackgroundColor(ContextCompat.getColor(itemView.context, getRandomColor()))
    }

    private fun getRandomColor() = arrayOf(
            R.color.colorEventLightGreen,
            R.color.colorEventDarkBlue,
            R.color.colorEventDarkGreen,
            R.color.colorEventLightBlue,
            R.color.colorEventOrange
    ).random()

}
