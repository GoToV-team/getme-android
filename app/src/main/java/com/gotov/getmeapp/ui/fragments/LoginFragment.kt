package com.gotov.getmeapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentLoginBinding
import com.gotov.getmeapp.ui.utils.navigateSafely

class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginRegisterLink.setOnClickListener {
            findNavController().navigateSafely(R.id.action_LoginPage_to_RegisterPage)
        }

        binding.loginLoginButton.setOnClickListener {
            findNavController().navigateSafely(R.id.action_global_mainFlowFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}