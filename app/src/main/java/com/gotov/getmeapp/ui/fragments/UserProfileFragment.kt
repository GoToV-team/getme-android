package com.gotov.getmeapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentProfileBinding
import com.gotov.getmeapp.data.getUsers

class UserProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val userId = arguments?.getInt("user_id")
        val user = userId?.let { getUsers().get(userId)}
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}