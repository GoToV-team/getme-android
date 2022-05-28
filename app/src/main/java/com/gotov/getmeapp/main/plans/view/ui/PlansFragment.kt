package com.gotov.getmeapp.main.plans.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentPlansPageBinding
import com.gotov.getmeapp.main.plan.model.data.Plan
import com.gotov.getmeapp.main.plan.model.data.getPlans
import com.gotov.getmeapp.main.plans.model.data.Menti
import com.gotov.getmeapp.main.plans.view.items.MentisViewAdapter
import com.gotov.getmeapp.main.plans.view.items.PlansViewAdapter

class PlansFragment : Fragment(R.layout.fragment_plans_page) {
    private val binding by viewBinding(FragmentPlansPageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val status = arguments?.getString("status")
        val test: Array<Plan> = getPlans()

        val testMenti: Array<Menti> = arrayOf(
            Menti("Dore", "Я хороший"),
            Menti("Dore", "Я хороший"),
            Menti("Dore", "Я хороший"),
            Menti("Dore", "Я хороший"),
            Menti("Dore", "Я хороший")
        )

        val rec: RecyclerView = binding.planList
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        rec.layoutManager = layoutManager
        rec.adapter = PlansViewAdapter(test)

        val recMenti: RecyclerView = binding.mentiList
        val layoutManagerMenti: RecyclerView.LayoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recMenti.layoutManager = layoutManagerMenti
        recMenti.adapter = MentisViewAdapter(testMenti)

        if (status == "menti") {
            binding.radioMenti.isChecked = false
            binding.radioMentor.isChecked = true
            binding.mentiList.visibility = View.GONE
            binding.dividerBetweenMentiAndPlan.visibility = View.GONE
        } else {
            binding.radioMenti.isChecked = true
            binding.radioMentor.isChecked = false
            binding.mentiList.visibility = View.VISIBLE
            binding.dividerBetweenMentiAndPlan.visibility = View.VISIBLE
        }

        binding.radioMenti.setOnClickListener {
            binding.mentiList.visibility = View.VISIBLE
            binding.dividerBetweenMentiAndPlan.visibility = View.VISIBLE
        }

        binding.radioMentor.setOnClickListener {
            binding.mentiList.visibility = View.GONE
            binding.dividerBetweenMentiAndPlan.visibility = View.GONE
        }
    }
}
