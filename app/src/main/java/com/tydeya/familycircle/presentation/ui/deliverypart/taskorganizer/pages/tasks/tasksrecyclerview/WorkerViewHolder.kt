package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview

import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.databinding.RecyclerItemTaskWorkerBinding

class WorkerViewHolder(
        private val binding: RecyclerItemTaskWorkerBinding
) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(workerName: String) {
        binding.root.text = workerName
    }

}
