package com.gotov.getmeapp.sign.login.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentLoginBinding
import com.gotov.getmeapp.sign.login.model.data.Login
import com.gotov.getmeapp.sign.login.viewmodel.LoginStatus
import com.gotov.getmeapp.sign.login.viewmodel.LoginViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.activityNavController
import com.gotov.getmeapp.utils.ui.navigateSafely
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadingLogin.visibility = View.GONE

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            loginViewModel.status.collect {
                when (it) {
                    is Resource.Success -> {
                        when (it.data) {
                            LoginStatus.SUCCESS ->
                                activityNavController()
                                    .navigateSafely(R.id.action_global_mainFlowFragment)
                            else -> {}
                        }
                        binding.loadingLogin.visibility = View.GONE
                        binding.loginLoginButton.visibility = View.VISIBLE
                        binding.loginLoginButton.isClickable = true
                    }
                    is Resource.Loading -> {
                        binding.loadingLogin.visibility = View.VISIBLE
                        binding.loginLoginButton.visibility = View.INVISIBLE
                        binding.loginLoginButton.isClickable = false
                    }
                    is Resource.Error -> {
                        binding.loadingLogin.visibility = View.GONE
                        binding.loginLoginButton.visibility = View.VISIBLE
                        binding.loginLoginButton.isClickable = true
                    }
                    else -> {}
                }
            }
        }

        binding.loginRegisterLink.setOnClickListener {
            findNavController().navigateSafely(R.id.action_LoginPage_to_RegisterPage)
        }

        binding.loginLoginButton.setOnClickListener {
            val login = binding.loginUsernameInput.text.toString()
            val password = binding.loginPasswordInput.text.toString()

            loginViewModel.login(Login(login, password))
        }
    }
}
