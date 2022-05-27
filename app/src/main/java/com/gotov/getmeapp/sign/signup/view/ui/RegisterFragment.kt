package com.gotov.getmeapp.sign.signup.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentRegisterBinding
import com.gotov.getmeapp.sign.login.model.data.Login
import com.gotov.getmeapp.sign.login.viewmodel.LoginStatus
import com.gotov.getmeapp.sign.login.viewmodel.LoginViewModel
import com.gotov.getmeapp.sign.signup.model.data.Register
import com.gotov.getmeapp.sign.signup.viewmodel.RegisterStatus
import com.gotov.getmeapp.sign.signup.viewmodel.RegisterViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.activityNavController
import com.gotov.getmeapp.utils.ui.navigateSafely
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)

    private val registerViewModel by viewModel<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            registerViewModel.status.collect {
                when (it) {
                    is Resource.Success -> {
                        when (it.data) {
                            RegisterStatus.SUCCESS ->
                                findNavController().navigateUp()
                            else -> {}
                        }
                        binding.registerRegisterButton.isClickable = true
                    }
                    is Resource.Loading -> {
                        binding.registerRegisterButton.isClickable = false
                    }
                    is Resource.Error -> {
                        binding.registerRegisterButton.isClickable = true
                    }
                    else -> {}
                }
            }
        }

        binding.registerRegisterButton.setOnClickListener {
            val login = binding.registerUsernameInput.text.toString()
            val password = binding.registerPasswordInput.text.toString()

            registerViewModel.register(Register(login, password))
        }
    }
}
