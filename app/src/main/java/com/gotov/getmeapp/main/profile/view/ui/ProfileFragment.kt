package com.gotov.getmeapp.main.profile.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.profileWatchContacts.visibility = View.GONE
        binding.profileWatchContactsBtnMessage.visibility = View.GONE
        binding.profileWatchContactsBtnStartWork.visibility = View.GONE
        return binding.root
    }
}
