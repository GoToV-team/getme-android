package com.gotov.getmeapp.ui.items

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
import com.gotov.getmeapp.data.Task

class TaskViewAdapter(tasks : Array<Task>) : RecyclerView.Adapter<TaskItemFragment>() {

    private val _tasks : Array<Task> = tasks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemFragment {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_plan_task_item, parent, false)
        return TaskItemFragment(view)
    }

    override fun onBindViewHolder(holder: TaskItemFragment, position: Int) {
        val plan = _tasks[position]
        holder.bind(plan)
    }

    override fun getItemCount(): Int {
        return _tasks.size
    }

}

class TaskItemFragment(itemView: View) : RecyclerView.ViewHolder(itemView) {
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


/*
private const val ARG_TITLE = "title"
private const val ARG_DESCRIPTION = "description"
private const val ARG_STATE = "state"

/**
 * A simple [Fragment] subclass.
 * Use the [PlanTaskItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanTaskItemFragment : Fragment() {
    private var title: String? = null
    private var description: String? = null
    private var state: Boolean? = null

    private var _binding: FragmentPlanTaskItemBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESCRIPTION)
            state = it.getBoolean(ARG_STATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanTaskItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.planItemTitle.text = savedInstanceState?.getString(ARG_TITLE)
        binding.planItemDescription.text = savedInstanceState?.getString(ARG_DESCRIPTION)

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title Parameter 1.
         * @param description Parameter 2.
         * @return A new instance of fragment PlanTaskItemFragment.
         */
        @JvmStatic
        fun newInstance(title: String, description: String, state: Boolean) =
            PlanTaskItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                    putString(ARG_STATE, state.toString())
                }
            }
    }
}
*/