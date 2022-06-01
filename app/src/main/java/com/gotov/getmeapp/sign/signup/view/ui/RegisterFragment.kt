package com.gotov.getmeapp.sign.signup.view.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentRegisterBinding
import com.gotov.getmeapp.sign.signup.model.data.Register
import com.gotov.getmeapp.sign.signup.viewmodel.RegisterStatus
import com.gotov.getmeapp.sign.signup.viewmodel.RegisterViewModel
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)

    private val registerViewModel by viewModel<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadingRegister.visibility = View.GONE
        binding.registerErrorText.visibility = View.GONE

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            registerViewModel.status.collect {
                when (it) {
                    is Resource.Success -> {
                        when (it.data) {
                            RegisterStatus.SUCCESS -> showRegisterDialog()
                            else -> {}
                        }
                        binding.registerRegisterButton.isClickable = true
                        binding.loadingRegister.visibility = View.GONE
                        binding.registerRegisterButton.visibility = View.VISIBLE
                        binding.registerErrorText.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.registerRegisterButton.isClickable = false
                        binding.registerRegisterButton.visibility = View.INVISIBLE
                        binding.loadingRegister.visibility = View.VISIBLE
                        binding.registerErrorText.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.registerRegisterButton.isClickable = true
                        binding.loadingRegister.visibility = View.GONE
                        binding.registerRegisterButton.visibility = View.VISIBLE
                        binding.registerErrorText.visibility = View.VISIBLE
                        binding.registerErrorText.text = it.msg
                    }
                    else -> {}
                }
            }
        }

        binding.registerUsernameInput.doOnTextChanged { text, _, _, _ ->
            binding.registerUsernameInput.error = Register.validateLogin(text.toString())
        }

        binding.registerPasswordInput.doOnTextChanged { text, _, _, _ ->
            binding.registerPasswordInput.error = Register.validatePassword(text.toString())
        }

        binding.registerRegisterButton.setOnClickListener {
            val login = binding.registerUsernameInput.text.toString()
            val password = binding.registerPasswordInput.text.toString()
            val entity = Register(login, password)

            if (validate(entity)) {
                registerViewModel.register(Register(login, password))
            }
        }
    }

    private fun showRegisterDialog() {
        val newFragment = ContinueRegisterDialogFragment(registerViewModel)
        newFragment.show(childFragmentManager, "continue")
    }

    private fun validate(register: Register): Boolean {
        val loginError = Register.validateLogin(register.login)
        val passwordError = Register.validatePassword(register.password)

        binding.registerUsernameInput.error = loginError
        binding.registerPasswordInput.error = passwordError

        return passwordError == null && loginError == null
    }
}
