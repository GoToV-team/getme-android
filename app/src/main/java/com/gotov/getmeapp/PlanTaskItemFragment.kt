package com.gotov.getmeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gotov.getmeapp.databinding.FragmentPlanTaskItemBinding

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
