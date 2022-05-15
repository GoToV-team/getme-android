package com.gotov.getmeapp.sign_module.login.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentLoginBinding
import com.gotov.getmeapp.sign_module.login.model.data.Login
import com.gotov.getmeapp.sign_module.login.view_model.LoginStatus
import com.gotov.getmeapp.sign_module.login.view_model.LoginViewModel
import com.gotov.getmeapp.utils.model.LoadingState
import com.gotov.getmeapp.utils.model.Status
import com.gotov.getmeapp.utils.ui.activityNavController
import com.gotov.getmeapp.utils.ui.navigateSafely
import io.reactivex.android.schedulers.AndroidSchedulers

class LoginFragment : Fragment() {
    private val binding by viewBinding(FragmentLoginBinding::bind)

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginRegisterLink.setOnClickListener {
            findNavController().navigateSafely(R.id.action_LoginPage_to_RegisterPage)
        }

        binding.loginLoginButton.setOnClickListener {
            val login = binding.loginUsernameText.text.toString()
            val password = binding.loginPasswordText.text.toString()
            activity?.let { act ->
                loginViewModel.login(Login(login, password))
                    .observe(act) {
                        when (it.state.status) {
                            Status.SUCCESS -> {
                                when (it.data) {
                                    LoginStatus.SUCCESS ->
                                        activityNavController().navigateSafely(R.id.action_global_mainFlowFragment)
                                    else -> {}
                                }
                            }
                            Status.FAILED -> {}
                            else -> {}
                        }
                    }
            }

        }
    }
}
