package com.gotov.getmeapp.main.plan.view.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gotov.getmeapp.R
import com.gotov.getmeapp.main.plan.model.data.Task

class TaskViewAdapter(
    tasks: ArrayList<Task>,
    private val onApply: (task: Task) -> Unit
) : RecyclerView.Adapter<TaskItemHolder>() {

    private val _tasks: ArrayList<Task> = tasks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_plan_task_item,
            parent,
            false
        )
        return TaskItemHolder(view, onApply)
    }

    override fun onBindViewHolder(holder: TaskItemHolder, position: Int) {
        val task = _tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return _tasks.size
    }

    fun setData(users: Collection<Task>) {
        _tasks.clear()
        _tasks.addAll(users)
    }

    fun getData(): List<Task> {
        return _tasks.toList()
    }
}

class TaskItemHolder(
    itemView: View,
    private val onApply: (task: Task) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val _checkBox: CheckBox = itemView.findViewById(R.id.task_item_checkbox)
    private val _title: TextView = itemView.findViewById(R.id.task_item_title)
    private val _description: TextView = itemView.findViewById(R.id.task_item_description)
    private val _deadline: TextView = itemView.findViewById(R.id.task_item_data)

    fun bind(task: Task) {
        task.addToViews(_title, _description, _checkBox, _deadline)
        _checkBox.isClickable = true
        _checkBox.setOnCheckedChangeListener { _, _ ->
            onApply(task)
            _checkBox.isClickable = false
        }
    }
}
