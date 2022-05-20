package com.gotov.getmeapp.main.profile.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentProfileBinding
import com.gotov.getmeapp.main.profile.model.data.getUsers

class UserProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userId = arguments?.getInt("user_id")
        val user = userId?.let { getUsers()[userId] }
        user?.addToViews(binding.profileWatchHeaderFio,
            binding.profileWatchAbout, binding.profileWatchSkills, this.context)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileWatchContactsBtnStartWork.setOnClickListener {
            findNavController().navigate(R.id.action_UserFragment_to_PlansFragment)
        }

    }
}
