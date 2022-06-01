package com.gotov.getmeapp.main.editprofile.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentEditProfileBinding
import com.gotov.getmeapp.main.editprofile.model.data.UpdateUser
import com.gotov.getmeapp.main.editprofile.model.data.User
import com.gotov.getmeapp.main.editprofile.viewmodel.EditProfileViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.MultiSelectInput
import com.gotov.getmeapp.utils.ui.displayToastOnTop
import com.gotov.getmeapp.utils.ui.fieldEmptyError
import com.gotov.getmeapp.utils.ui.setOnEmptyError
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

enum class ChangedFields(val data: Int) {
    About(1),
    FirstName(2),
    LastName(4),
    TgTag(8),
    Status(16),
    Skills(32)
}

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private val binding by viewBinding(FragmentEditProfileBinding::bind)

    private val editProfileViewModel by viewModel<EditProfileViewModel>()

    private var skills: ArrayList<String> = arrayListOf()
    private var isSelectedSkills: ArrayList<Boolean> = arrayListOf()
    private var multiSelectInput: MultiSelectInput? = null

    private var changedFields = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editProfileCircyleLoadProgress.visibility = View.GONE

        runLifeCycles()
        setListeners()
    }

    private fun setListeners() {
        binding.editProfileStatus.setOnCheckedChangeListener { _, s ->
            changedFields = changedFields or ChangedFields.Status.data
            if (s) {
                binding.editProfileStatus.text = User.MentorText
            } else {
                binding.editProfileStatus.text = User.MentiText
            }
            binding.editProfilePageApplyButton.visibility = View.VISIBLE
        }

        binding.editProfilePageTgTag.setOnEmptyError { _, _, _, _ ->
            changedFields = changedFields or ChangedFields.TgTag.data
            binding.editProfilePageApplyButton.visibility = View.VISIBLE
        }

        binding.editProfilePageFirstName.setOnEmptyError { _, _, _, _ ->
            changedFields = changedFields or ChangedFields.FirstName.data
            binding.editProfilePageApplyButton.visibility = View.VISIBLE
        }

        binding.editProfilePageLastName.setOnEmptyError { _, _, _, _ ->
            changedFields = changedFields or ChangedFields.LastName.data
            binding.editProfilePageApplyButton.visibility = View.VISIBLE
        }

        binding.editProfilePageAboutInput.doOnTextChanged { _, _, _, _ ->
            changedFields = changedFields or ChangedFields.About.data
            binding.editProfilePageApplyButton.visibility = View.VISIBLE
        }

        binding.editProfilePageSkillsInput.doOnTextChanged { _, _, _, _ ->
            changedFields = changedFields or ChangedFields.Skills.data
            binding.editProfilePageApplyButton.visibility = View.VISIBLE
        }

        binding.editProfilePageApplyButton.setOnClickListener {
            val user = UpdateUser()

            if (changedFields and ChangedFields.Skills.data > 0) {
                multiSelectInput?.let {
                    user.skills = it.getSelectedSkills()
                }
            }

            if (changedFields and ChangedFields.About.data > 0) {
                user.about = binding.editProfilePageAboutInput.text.toString()
            }

            if (changedFields and ChangedFields.LastName.data > 0) {
                user.lastName = binding.editProfilePageLastName.text.toString()
            }

            if (changedFields and ChangedFields.FirstName.data > 0) {
                user.firstName = binding.editProfilePageFirstName.text.toString()
            }

            if (changedFields and ChangedFields.TgTag.data > 0) {
                user.tgTag = binding.editProfilePageTgTag.text.toString()
            }

            if ((
                user.tgTag.isNotEmpty() ||
                    changedFields and ChangedFields.TgTag.data == 0
                ) &&
                (
                    user.firstName.isNotEmpty() ||
                        changedFields and ChangedFields.FirstName.data == 0
                    ) &&
                (
                    user.lastName.isNotEmpty() ||
                        changedFields and ChangedFields.LastName.data == 0
                    ) &&
                (
                    user.skills.isNotEmpty() ||
                        changedFields and ChangedFields.Skills.data == 0
                    )
            ) {
                if (changedFields and ChangedFields.Status.data > 0) {
                    editProfileViewModel.changeStatus()
                }
                editProfileViewModel.updateUser(user)
            } else {
                if (user.skills.isEmpty() && changedFields and ChangedFields.Skills.data > 0) {
                    binding.editProfilePageSkillsInput.error = fieldEmptyError
                } else {
                    if (changedFields and ChangedFields.Status.data > 0) {
                        editProfileViewModel.changeStatus()
                    }
                }
            }
        }
    }

    private fun startLoadingPage() {
        binding.editProfileLoadProgress.visibility = View.VISIBLE
        binding.editProfilePageAvatar.visibility = View.GONE
        binding.editProfilePageTgTag.visibility = View.GONE
        binding.editProfilePageSkillsInput.visibility = View.GONE
        binding.editProfilePageAboutInput.visibility = View.GONE
        binding.editProfilePageLastName.visibility = View.GONE
        binding.editProfilePageFirstName.visibility = View.GONE
        binding.editProfilePageApplyButton.visibility = View.GONE
        binding.editProfileStatus.visibility = View.GONE
    }

    private fun stopLoadingPage() {
        binding.editProfileLoadProgress.visibility = View.GONE
        binding.editProfilePageAvatar.visibility = View.VISIBLE
        binding.editProfilePageTgTag.visibility = View.VISIBLE
        binding.editProfilePageSkillsInput.visibility = View.VISIBLE
        binding.editProfilePageAboutInput.visibility = View.VISIBLE
        binding.editProfilePageLastName.visibility = View.VISIBLE
        binding.editProfilePageFirstName.visibility = View.VISIBLE
        binding.editProfileStatus.visibility = View.VISIBLE
    }

    private fun startLoadingUpdatePage() {
        binding.editProfilePageApplyButton.visibility = View.INVISIBLE
        binding.editProfileCircyleLoadProgress.visibility = View.VISIBLE
    }

    private fun stopLoadingUpdatePage() {
        binding.editProfilePageApplyButton.visibility = View.VISIBLE
        binding.editProfileCircyleLoadProgress.visibility = View.GONE
    }

    private fun runLifeCycles() {
        runSkillsLifeCycle()
        runUserLifeCycle()
        runUpdateUserLifeCycle()
        runChangeStatusLifeCycle()
    }

    private fun runChangeStatusLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            editProfileViewModel.statusUser.collect {
                when (it) {
                    is Resource.Success -> {
                        displayToastOnTop(
                            context,
                            "Успешное обновление статуса",
                            Toast.LENGTH_SHORT
                        )
                        stopLoadingUpdatePage()
                        editProfileViewModel.setNullStatus()
                        changedFields = 0
                        binding.editProfilePageApplyButton.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        startLoadingUpdatePage()
                    }
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка обновления ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        stopLoadingUpdatePage()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun runUpdateUserLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            editProfileViewModel.isUpdated.collect {
                when (it) {
                    is Resource.Success -> {
                        displayToastOnTop(
                            context,
                            "Успешное обновление полей",
                            Toast.LENGTH_SHORT
                        )
                        stopLoadingUpdatePage()
                        editProfileViewModel.setNullUpdateStatus()
                        changedFields = 0
                        binding.editProfilePageApplyButton.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        startLoadingUpdatePage()
                    }
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка обновления ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        stopLoadingUpdatePage()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun runSkillsLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            editProfileViewModel.skills.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.run {
                            skills = this.map { name -> name } as ArrayList<String>
                            isSelectedSkills = this.map { false } as ArrayList<Boolean>
                        }
                        editProfileViewModel.getCurrentUser()
                        stopLoadingPage()
                    }
                    is Resource.Loading -> {
                        startLoadingPage()
                    }
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка загрузки страницы ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        stopLoadingPage()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun runUserLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            editProfileViewModel.user.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.addToViews(
                            binding.editProfilePageFirstName,
                            binding.editProfilePageLastName,
                            binding.editProfilePageAboutInput,
                            binding.editProfilePageTgTag,
                            binding.editProfilePageAvatar,
                            binding.editProfileStatus
                        )

                        it.data?.let { user ->
                            skills.filterIndexed { i, s ->
                                isSelectedSkills[i] = user.skills.contains(s)
                                return@filterIndexed true
                            }
                        }

                        multiSelectInput = MultiSelectInput(
                            binding.editProfilePageSkillsInput,
                            skills,
                            isSelectedSkills
                        )

                        multiSelectInput?.setupClickListeners()
                        changedFields = 0
                        binding.editProfilePageApplyButton.visibility = View.GONE
                        stopLoadingPage()
                    }
                    is Resource.Loading -> {
                        startLoadingPage()
                    }
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка загрузки страницы ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        stopLoadingPage()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startLoadingPage()
        editProfileViewModel.getSkills()
    }
}
