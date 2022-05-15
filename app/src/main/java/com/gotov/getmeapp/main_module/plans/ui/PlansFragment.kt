package com.gotov.getmeapp.main_module.plans.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.main_module.plans.data.Menti
import com.gotov.getmeapp.main_module.plan.data.Plan
import com.gotov.getmeapp.main_module.plan.data.getPlans
import com.gotov.getmeapp.databinding.FragmentPlansPageBinding
import com.gotov.getmeapp.main_module.plans.items.MentisViewAdapter
import com.gotov.getmeapp.main_module.plans.items.PlansViewAdapter

class PlansFragment : Fragment() {
    private val binding by viewBinding(FragmentPlansPageBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
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

        binding.radioMenti.isChecked = true
        binding.radioMentor.isChecked = false
        binding.mentiList.visibility = View.VISIBLE
        binding.dividerBetweenMentiAndPlan.visibility = View.VISIBLE

        binding.radioMenti.setOnClickListener {
            binding.mentiList.visibility = View.VISIBLE
            binding.dividerBetweenMentiAndPlan.visibility = View.VISIBLE
        }

        binding.radioMentor.setOnClickListener {
            binding.mentiList.visibility = View.GONE
            binding.dividerBetweenMentiAndPlan.visibility = View.GONE
        }

        return binding.root
    }
}
