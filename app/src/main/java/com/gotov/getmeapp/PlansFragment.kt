package com.gotov.getmeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gotov.getmeapp.databinding.FragmentPlansPageBinding

class PlansFragment : Fragment() {
    private var _binding: FragmentPlansPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val test : Array<Plan> = getPlans()

        val testMenti : Array<Menti> = arrayOf(
            Menti("Dore", "Я хороший"),
            Menti("Dore", "Я хороший"),
            Menti("Dore", "Я хороший"),
            Menti("Dore", "Я хороший"),
            Menti("Dore", "Я хороший")
        )

        _binding = FragmentPlansPageBinding.inflate(inflater, container, false)
        val rec : RecyclerView = binding.planList
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        rec.layoutManager = layoutManager
        rec.adapter = PlansViewAdapter(test)

        val recMenti : RecyclerView = binding.mentiList
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}