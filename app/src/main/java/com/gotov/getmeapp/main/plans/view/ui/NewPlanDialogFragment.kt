package com.gotov.getmeapp.main.plans.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentCreatePlanBinding
import com.gotov.getmeapp.main.plans.viewmodel.SharedViewModel

class NewPlanDialogFragment : DialogFragment() {

    private lateinit var viewModel: SharedViewModel

    private var _binding: FragmentCreatePlanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePlanBinding.bind(
            inflater.inflate(
                R.layout.fragment_create_plan,
                container
            )
        )
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners() {
        binding.newPlanCreateButton.setOnClickListener {
            viewModel.sendName(binding.newPlanTitleInput.text.toString())
            dismiss()
        }
    }
}
