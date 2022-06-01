package com.gotov.getmeapp.main.profile.view.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentProfileBinding
import com.gotov.getmeapp.main.profile.viewmodel.ProfileViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.model.removeTgTag
import com.gotov.getmeapp.utils.ui.displayToastOnTop
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val profileViewModel by viewModel<ProfileViewModel>()

    private var userId: Int? = null

    companion object {
        private val textSendOffer = "Заявка на менторство отправлина," +
            " ожидайте появления плана от ментора или его письма"

        private val tgUrl = "https://t.me/"
    }

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
                            binding.profileWatchContactsBtnMessage.setOnClickListener { _ ->
                                it.data?.let { user ->
                                    val browserIntent =
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("$tgUrl/${user.TgTag.removeTgTag()}")
                                        )
                                    startActivity(browserIntent)
                                }
                            }
                        }
                        binding.loadPageList.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.loadPageList.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка получения пользователя ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
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
                                val toast =
                                    Toast.makeText(context, textSendOffer, Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.TOP or Gravity.CENTER, 0, 0)
                                toast.show()

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
                        displayToastOnTop(
                            context,
                            "Произошла ошибка получения пользователя ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
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
