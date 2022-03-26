package com.gotov.getmeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val ARG_TITLE = "title"
private const val ARG_DESCRIPTION = "description"
private const val ARG_STATE = "state"

/**
 * A simple [Fragment] subclass.
 * Use the [PlanTaskItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanTaskItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var title: String? = null
    private var description: String? = null
    private var state: Boolean? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan_task_item, container, false)
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
        // TODO: Rename and change types and number of parameters
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
