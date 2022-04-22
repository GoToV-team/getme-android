package com.gotov.getmeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.data.Plan

class PlansViewAdapter(plans : Array<Plan>) : RecyclerView.Adapter<PlanItemFragment>() {

    private val _plans : Array<Plan> = plans

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanItemFragment {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.task_info, parent, false)
        return PlanItemFragment(view)
    }

    override fun onBindViewHolder(holder: PlanItemFragment, position: Int) {
        val plan = _plans[position]
        holder.bind(plan)
    }

    override fun getItemCount(): Int {
        return _plans.size
    }

}

class PlanItemFragment(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val _namePlan: TextView = itemView.findViewById(R.id.plan_item_title)
    private val _progress: ProgressBar = itemView.findViewById(R.id.plan_progress_bar)
    private val _skills: ChipGroup = itemView.findViewById(R.id.plan_item_skills)

    fun bind(plan: Plan) {
        plan.addToViews(_namePlan,null,
            _progress,  _skills, itemView.context)

        var navController: NavController?

        this.apply {
            itemView.setOnClickListener {
                navController = findNavController(itemView)
                val args: Bundle = bundleOf("plan_id" to plan.id)
                navController!!.navigate(R.id.action_PlansFragment_to_PlanFragment, args)
            }
        }
    }
}
