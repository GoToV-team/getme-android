package com.gotov.getmeapp.main.plans.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentCreatePlanBinding
import com.gotov.getmeapp.main.plans.model.data.OffersRequest
import com.gotov.getmeapp.main.plans.viewmodel.PlansViewModel
import com.gotov.getmeapp.main.plans.viewmodel.SharedViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.MultiSelectInput
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewPlanDialogFragment(
    private val offerId: Int,
    private val plansViewModel: PlansViewModel
) : DialogFragment() {

    companion object {
        private const val fieldError = "Поле не должно быть пустым"
    }

    private var skills: ArrayList<String> = arrayListOf()
    private var isSelectedSkills: ArrayList<Boolean> = arrayListOf()

    private var _binding: FragmentCreatePlanBinding? = null
    private val binding get() = _binding!!

    private var multiSelectInput: MultiSelectInput? = null

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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            plansViewModel.skills.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.run {
                            skills = this.map { name -> name } as ArrayList<String>
                            isSelectedSkills = this.map { false } as ArrayList<Boolean>
                            multiSelectInput = MultiSelectInput(
                                binding.newPlanSkillsInput,
                                skills,
                                isSelectedSkills
                            )
                            multiSelectInput?.setupClickListeners()
                        }
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                    else -> {}
                }
            }
        }

        plansViewModel.getSkills()

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
            var skills: List<String> = listOf()
            multiSelectInput?.let {
                skills = it.getSelectedSkills()
            }
            val title = binding.newPlanTitleInput.text.toString()
            val about = binding.newPlanAboutInput.text.toString()

            if (title.isNotEmpty() && about.isNotEmpty() && skills.isNotEmpty()) {
                plansViewModel.applyMenti(offerId, OffersRequest(about, skills, title))
                dismiss()
            } else {
                if (skills.isEmpty()) {
                    binding.newPlanSkillsInput.error = fieldError
                }
            }
        }

        binding.newPlanTitleInput.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isEmpty()) {
                    binding.newPlanTitleInput.error = fieldError
                }
            } else {
                binding.newPlanTitleInput.error = fieldError
            }
        }

        binding.newPlanAboutInput.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isEmpty()) {
                    binding.newPlanAboutInput.error = fieldError
                }
            } else {
                binding.newPlanAboutInput.error = fieldError
            }
        }
    }
}
