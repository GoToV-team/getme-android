package com.gotov.getmeapp.main.plans.view.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R

class PlansViewAdapter(
    plans: ArrayList<com.gotov.getmeapp.main.plans.model.data.Plan>,
    private val onClick: (plan: com.gotov.getmeapp.main.plans.model.data.Plan) -> Unit
) : RecyclerView.Adapter<PlanItemHolder>() {

    private val _plans: ArrayList<com.gotov.getmeapp.main.plans.model.data.Plan> = plans

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanItemHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.task_info,
            parent,
            false
        )
        return PlanItemHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PlanItemHolder, position: Int) {
        val plan = _plans[position]
        holder.bind(plan)
    }

    override fun getItemCount(): Int {
        return _plans.size
    }

    fun setData(plans: Collection<com.gotov.getmeapp.main.plans.model.data.Plan>) {
        _plans.clear()
        _plans.addAll(plans)
    }

    fun getData(): List<com.gotov.getmeapp.main.plans.model.data.Plan> {
        return _plans.toList()
    }
}

class PlanItemHolder(
    itemView: View,
    private val onClick: (plan: com.gotov.getmeapp.main.plans.model.data.Plan) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val _namePlan: TextView = itemView.findViewById(R.id.plan_item_title)
    private val _progress: ProgressBar = itemView.findViewById(R.id.plan_progress_bar)
    private val _skills: ChipGroup = itemView.findViewById(R.id.plan_item_skills)

    fun bind(plan: com.gotov.getmeapp.main.plans.model.data.Plan) {
        plan.addToViews(
            _namePlan,
            null,
            _progress,
            _skills,
            itemView.context
        )

        this.apply {
            itemView.setOnClickListener {
                onClick(plan)
            }
        }
    }
}
