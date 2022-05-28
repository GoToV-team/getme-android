package com.gotov.getmeapp.main.profile.view.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentProfileBinding
import com.gotov.getmeapp.main.profile.viewmodel.ProfileViewModel
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val profileViewModel by viewModel<ProfileViewModel>()

    private var userId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                        binding.loadPageList.visibility = View.GONE
                    }
                    else -> {}
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileViewModel.isAdded.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { isAdded ->
                            if (isAdded) {
                                val args: Bundle = bundleOf("status" to "menti")
                                findNavController().navigate(
                                    R.id.action_UserFragment_to_PlansFragment,
                                    args
                                )
                            }
                        }
                        binding.loadPageList.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.loadPageList.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        binding.loadPageList.visibility = View.GONE
                    }
                    else -> {}
                }
            }
        }

        binding.profileWatchContactsBtnStartWork.setOnClickListener {
            userId?.let {
                profileViewModel.startMentor(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.profileWatchHeaderRole.text = "Ментор"
        binding.loadPageList.visibility = View.VISIBLE
        userId = arguments?.getInt("user_id")

        userId?.let {
            profileViewModel.getUserById(it)
        }
    }
}
