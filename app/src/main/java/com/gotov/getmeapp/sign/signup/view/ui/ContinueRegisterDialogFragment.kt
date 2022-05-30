package com.gotov.getmeapp.sign.signup.view.ui

import android.graphics.Rect
import android.os.Bundle
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentRegisterDialogBinding
import com.gotov.getmeapp.sign.signup.viewmodel.RegisterViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.MultiSelectInput
import com.gotov.getmeapp.utils.ui.displayToastOnTop
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContinueRegisterDialogFragment : DialogFragment() {

    private val registerViewModel by viewModel<RegisterViewModel>()

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

        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        registerViewModel.getSkills()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}
