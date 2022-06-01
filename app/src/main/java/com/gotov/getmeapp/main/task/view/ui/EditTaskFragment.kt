package com.gotov.getmeapp.main.task.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FragmentEditTaskBinding
import com.gotov.getmeapp.main.task.viewmodel.TaskViewModel
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [EditTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTaskFragment : Fragment(R.layout.fragment_edit_task) {
    private val binding by viewBinding(FragmentEditTaskBinding::bind)

    private val taskViewModel by viewModel<TaskViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val navController = Navigation.findNavController(view)
            viewLifecycleOwner.lifecycleScope.launch() {

                taskViewModel.update(
                    binding.titleFieldEdit.text.toString(),
                    binding.descriptionFieldEdit.text.toString(),
                )
                navController.popBackStack()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            taskViewModel.task.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.titleFieldEdit.setText(it.data?.name)
                        binding.descriptionFieldEdit.setText(it.data?.about)
                    }
                    else -> {}
                }
            }
        }
    }
}
