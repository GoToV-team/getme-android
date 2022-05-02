package com.gotov.getmeapp.ui.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

fun AppCompatActivity.setToolbar(navController: NavController, toolbar: Toolbar) {
    val appBarConfiguration = AppBarConfiguration(navController.graph)

    this.setSupportActionBar(toolbar)
    this.setupActionBarWithNavController(navController, appBarConfiguration)
    this.supportActionBar?.setDisplayShowTitleEnabled(false)
    toolbar.setNavigationOnClickListener { navController.navigateUp() }
}

fun AppCompatActivity.showUpIcon() {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
}

fun AppCompatActivity.hideUpIcon() {
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    supportActionBar?.setDisplayShowHomeEnabled(false)
}
