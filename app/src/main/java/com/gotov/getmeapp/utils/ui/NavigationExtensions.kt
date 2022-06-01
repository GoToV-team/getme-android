package com.gotov.getmeapp.utils.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.gotov.getmeapp.R

fun Fragment.activityNavController() = requireActivity().findNavController(R.id.nav_host_fragment)

fun NavController.navigateSafely(@IdRes actionId: Int) {
    if (graph.getAction(actionId) == null) {
        currentDestination?.getAction(actionId)?.let { navigate(actionId) }
    } else {
        graph.getAction(actionId)?.let { navigate(actionId) }
    }
}

fun NavController.navigateSafely(directions: NavDirections) {
    if (graph.getAction(directions.actionId) == null) {
        currentDestination?.getAction(directions.actionId)?.let { navigate(directions) }
    } else {
        graph.getAction(directions.actionId)?.let { navigate(directions) }
    }
}
