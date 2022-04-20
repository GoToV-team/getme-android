package com.gotov.getmeapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gotov.getmeapp.databinding.FragmentPlanBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlanFragment : Fragment() {

    private var _binding: FragmentPlanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        val test : Array<Task> = getTasks()
        val planId = arguments?.getInt("plan_id")
        val plan = planId?.let { getPlans().get(planId)}
        plan?.addToViews(binding.planTitle, binding.planDescription,
            binding.progressBar, binding.planSkills, this.context)


        binding.userInfoSmallName.text = "Ваcя Пупкин"
        binding.userInfoSmallNote.text = "Ментор"

        val rec : RecyclerView = binding.taskList
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        rec.layoutManager = layoutManager
        rec.adapter = TaskViewAdapter(test)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
