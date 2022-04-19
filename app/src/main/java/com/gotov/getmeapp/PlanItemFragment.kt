package com.gotov.getmeapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView

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
    private val _skills: LinearLayout = itemView.findViewById(R.id.plan_item_skills)

    fun bind(plan: Plan) {
        _namePlan.text = plan.Title
        _progress.progress = plan.Progress

        for (skill in plan.Skills) {
            val tmp: TextView = TextView(itemView.context)
            tmp.text = skill
            tmp.setTextColor(Color.WHITE);
            tmp.setBackgroundColor(Color.BLACK);
            tmp.textSize = 11F;
            tmp.setPadding(5);
            _skills.addView(tmp)
        }
    }
}
