package com.gotov.getmeapp.sign.signup.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentRegisterDialogBinding
import com.gotov.getmeapp.sign.signup.model.data.UpdateUser
import com.gotov.getmeapp.sign.signup.viewmodel.RegisterViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.MultiSelectInput
import com.gotov.getmeapp.utils.ui.activityNavController
import com.gotov.getmeapp.utils.ui.displayToastOnTop
import com.gotov.getmeapp.utils.ui.fieldEmptyError
import com.gotov.getmeapp.utils.ui.navigateSafely
import com.gotov.getmeapp.utils.ui.setOnEmptyError
import kotlinx.coroutines.flow.collect

class ContinueRegisterDialogFragment(
    private val registerViewModel: RegisterViewModel
) : DialogFragment() {

    private var skills: ArrayList<String> = arrayListOf()
    private var isSelectedSkills: ArrayList<Boolean> = arrayListOf()

    private var _binding: FragmentRegisterDialogBinding? = null
    private val binding get() = _binding!!

    private var multiSelectInput: MultiSelectInput? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDialogBinding.bind(
            inflater.inflate(
                R.layout.fragment_register_dialog,
                container
            )
        )

        setEventListener()

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runLifeCycles()
        registerViewModel.getSkills()
    }

    private fun setEventListener() {
        binding.registerDialogNameInput.setOnEmptyError()
        binding.registerDialogSecondNameInput.setOnEmptyError()
        binding.registerDialogTgTagInput.setOnEmptyError()

        binding.registerDialogApplyButton.setOnClickListener {
            var skills: List<String> = listOf()
            multiSelectInput?.let {
                skills = it.getSelectedSkills()
            }
            val firstName = binding.registerDialogNameInput.text.toString()
            val secondName = binding.registerDialogSecondNameInput.text.toString()
            val about = binding.registerDialogAboutInput.text.toString()
            val tgTag = binding.registerDialogTgTagInput.text.toString()

            if (firstName.isNotEmpty() &&
                secondName.isNotEmpty() &&
                tgTag.isNotEmpty() &&
                skills.isNotEmpty()
            ) {
                registerViewModel.updateUser(
                    UpdateUser(firstName, secondName, about, tgTag, skills)
                )
                binding.registerDialogApplyButton.isClickable = false
            } else {
                if (skills.isEmpty()) {
                    binding.registerDialogSkillsInput.error = fieldEmptyError
                }
            }
        }
    }

    private fun runLifeCycles() {
        runStatusUpdateLifeCycle()
        runSkillLifeCycle()
    }

    private fun runStatusUpdateLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            registerViewModel.updateStatus.collect {
                when (it) {
                    is Resource.Success -> {
                        displayToastOnTop(
                            context,
                            "Вы успешно зарегестировались",
                            Toast.LENGTH_SHORT
                        )
                        binding.registerDialogApplyButton.isClickable = true
                        dismiss()
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка обновлеия юзера ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        binding.registerDialogApplyButton.isClickable = true
                    }
                    else -> {}
                }
            }
        }
    }

    private fun runSkillLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            registerViewModel.skills.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.run {
                            skills = this.map { name -> name } as ArrayList<String>
                            isSelectedSkills = this.map { false } as ArrayList<Boolean>
                            multiSelectInput = MultiSelectInput(
                                binding.registerDialogSkillsInput,
                                skills,
                                isSelectedSkills
                            )
                            multiSelectInput?.setupClickListeners()
                        }
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка загрузки страницы ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onStop() {
        super.onStop()
        activityNavController()
            .navigateSafely(R.id.action_global_mainFlowFragment)
    }
}
