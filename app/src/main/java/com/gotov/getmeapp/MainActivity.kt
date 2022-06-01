package com.gotov.getmeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.gotov.getmeapp.app.preference.AppPreferences
import com.gotov.getmeapp.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.core.component.inject

class MainActivity : AppCompatActivity() {
    private val appPreferences by inject<AppPreferences>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        val tmp = appPreferences.getHashSet(AppPreferences.Cookies)
        if (tmp == null || tmp.size == 0) {
            navGraph.setStartDestination(R.id.signFlowFragment)
        } else {
            navGraph.setStartDestination(R.id.mainFlowFragment)
        }

        navController.graph = navGraph
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }
}
