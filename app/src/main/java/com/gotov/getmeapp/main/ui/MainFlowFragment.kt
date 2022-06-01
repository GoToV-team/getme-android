package com.gotov.getmeapp.main.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FlowFragmentMainBinding
import com.gotov.getmeapp.main.viewmodel.MainFlowViewModel
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.ui.BaseFlowFragment
import com.gotov.getmeapp.utils.ui.activityNavController
import com.gotov.getmeapp.utils.ui.displayToastOnTop
import com.gotov.getmeapp.utils.ui.hideUpIcon
import com.gotov.getmeapp.utils.ui.navigateSafely
import com.gotov.getmeapp.utils.ui.setToolbar
import com.gotov.getmeapp.utils.ui.showUpIcon
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFlowFragment : BaseFlowFragment(
    R.layout.flow_fragment_main,
    R.id.nav_host_fragment_content_main
) {
    private val mainFlowViewModel by viewModel<MainFlowViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private val binding by viewBinding(FlowFragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runLogoutLifeCycle()

        (activity as AppCompatActivity).setToolbar(navController, binding.mainToolbar)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.PlansFragment -> {
                    (activity as AppCompatActivity).hideUpIcon()
                }
                R.id.SearchFragment -> {
                    (activity as AppCompatActivity).hideUpIcon()
                }
                R.id.EditProfileFragment -> {
                    (activity as AppCompatActivity).hideUpIcon()
                }
                else -> {
                    (activity as AppCompatActivity).showUpIcon()
                }
            }
        }
    }

    private fun runLogoutLifeCycle() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainFlowViewModel.status.collect {
                when (it) {
                    is Resource.Success -> {
                        activityNavController()
                            .navigateSafely(R.id.action_global_signFlowFragment)
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        displayToastOnTop(
                            context,
                            "Произошла ошибка ${it.msg}",
                            Toast.LENGTH_SHORT
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_flow_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                mainFlowViewModel.logout()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setupNavigation() {
        binding.navHostFragmentMain.setupWithNavController(navController)
    }
}
