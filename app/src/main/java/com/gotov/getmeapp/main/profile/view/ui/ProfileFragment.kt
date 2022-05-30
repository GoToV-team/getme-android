package com.gotov.getmeapp.main.profile.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentProfileBinding
import com.gotov.getmeapp.main.profile.viewmodel.ProfileViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.displayToastOnTop
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val profileViewModel by viewModel<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadPageList.visibility = View.GONE

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileViewModel.user.collect {
                when (it) {
                    is Resource.Success -> {
                        context?.let { ctx ->
                            it.data?.addToViews(
                                binding.profileWatchHeaderFio,
                                binding.profileWatchAbout,
                                binding.profileWatchSkills,
                                binding.profileWatchHeaderAvatar,
                                ctx
                            )
                        }
                        binding.loadPageList.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.loadPageList.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка загрузки страницы ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                        binding.loadPageList.visibility = View.GONE
                    }
                    else -> {}
                }
            }
        }

        profileViewModel.getCurrentUser()

        binding.profileWatchContacts.visibility = View.GONE
        binding.profileWatchContactsBtnMessage.visibility = View.GONE
        binding.profileWatchContactsBtnStartWork.visibility = View.GONE
    }
}
