package com.gotov.getmeapp.ui.flow_fragments

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gotov.getmeapp.R
import androidx.navigation.ui.NavigationUI
import com.gotov.getmeapp.databinding.FlowFragmentMainBinding
import com.gotov.getmeapp.ui.utils.BaseFlowFragment

class MainFlowFragment : BaseFlowFragment(
    R.layout.flow_fragment_main, R.id.nav_host_fragment_main
) {

    private val binding by viewBinding(FlowFragmentMainBinding::bind)

    override fun setupNavigation(navController: NavController) {
        binding.navHostFragmentMain.setupWithNavController(navController)
    }

   /*private fun setupNav() {

        /*navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.LoginPage -> {
                    hideBottomNav()
                    hideUpIcon()
                }
                R.id.RegisterPage -> {
                    hideBottomNav()
                    showUpIcon()
                }
                R.id.PlansFragment -> {
                    showBottomNav()
                    hideUpIcon()
                }
                R.id.SearchFragment -> {
                    showBottomNav()
                    hideUpIcon()
                }
                R.id.ProfileFragment -> {
                    showBottomNav()
                    hideUpIcon()
                }
                else -> {
                    showBottomNav()
                    showUpIcon()
                }
            }
        }*/
    }*/

   /* private fun showUpIcon() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    private fun hideUpIcon() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false);
        supportActionBar?.setDisplayShowHomeEnabled(false);
    }


    private fun showBottomNav() {
        bottomNav.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottomNav.visibility = View.GONE
    }*/

}