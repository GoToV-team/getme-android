package com.gotov.getmeapp.ui.flow_fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FlowFragmentMainBinding
import com.gotov.getmeapp.ui.utils.BaseFlowFragment
import com.gotov.getmeapp.ui.utils.hideUpIcon
import com.gotov.getmeapp.ui.utils.setToolbar
import com.gotov.getmeapp.ui.utils.showUpIcon

class MainFlowFragment : BaseFlowFragment(
    R.layout.flow_fragment_main,
    R.id.nav_host_fragment_content_main
) {

    private val binding by viewBinding(FlowFragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setToolbar(navController, binding.mainToolbar)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.PlansFragment -> {
                    (activity as AppCompatActivity).hideUpIcon()
                }
                R.id.SearchFragment -> {
                    (activity as AppCompatActivity).hideUpIcon()
                }
                R.id.ProfileFragment -> {
                    (activity as AppCompatActivity).hideUpIcon()
                }
                else -> {
                    (activity as AppCompatActivity).showUpIcon()
                }
            }
        }
    }

    override fun setupNavigation() {
        binding.navHostFragmentMain.setupWithNavController(navController)
    }
}
