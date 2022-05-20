package com.gotov.getmeapp.utils.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.gotov.getmeapp.R

fun Fragment.activityNavController() = requireActivity().findNavController(R.id.nav_host_fragment)

fun NavController.navigateSafely(@IdRes actionId: Int) {
    graph.getAction(actionId)?.let { navigate(actionId) }
}

fun NavController.navigateSafely(directions: NavDirections) {
    graph.getAction(directions.actionId)?.let { navigate(directions) }
}
