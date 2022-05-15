package com.gotov.getmeapp.main_module.edit_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.databinding.FlowFragmentMainBinding
import com.gotov.getmeapp.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private val binding by viewBinding(FragmentEditProfileBinding::bind)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editProfileSaveButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
