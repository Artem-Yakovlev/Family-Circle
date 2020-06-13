package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.databinding.RecyclerItemTaskWorkerBinding

class WorkersRecyclerViewAdapter(
        private val workersNames: List<String>
) :
        RecyclerView.Adapter<WorkerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            WorkerViewHolder(RecyclerItemTaskWorkerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        holder.bindData(workersNames[position])
    }

    override fun getItemCount() = workersNames.size
}
