package com.gotov.getmeapp.main.plan.view.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gotov.getmeapp.R
import com.gotov.getmeapp.main.task.model.data.Task

class TaskViewAdapter(tasks: List<Task>) : RecyclerView.Adapter<TaskItemHolder>() {

    private val _tasks: List<Task> = tasks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_plan_task_item,
            parent,
            false
        )
        return TaskItemHolder(view)
    }

    override fun onBindViewHolder(holder: TaskItemHolder, position: Int) {
        val plan = _tasks[position]
        holder.bind(plan)
    }

    override fun getItemCount(): Int {
        return _tasks.size
    }
}

class TaskItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val _checkBox: CheckBox = itemView.findViewById(R.id.task_item_checkbox)
    private val _title: TextView = itemView.findViewById(R.id.task_item_title)
    private val _description: TextView = itemView.findViewById(R.id.task_item_description)

    fun bind(task: Task) {
        task.addToViews(_title, _description, _checkBox)

        var navController: NavController?
        this.apply {
            itemView.setOnClickListener {
                navController = findNavController(itemView)
                val args: Bundle = bundleOf("task_id" to task.id)
                navController!!.navigate(R.id.action_PlanFragment_to_TaskFragment, args)
            }
        }
    }
}
