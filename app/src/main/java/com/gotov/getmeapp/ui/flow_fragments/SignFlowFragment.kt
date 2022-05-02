package com.gotov.getmeapp.ui.flow_fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FlowFragmentMainBinding
import com.gotov.getmeapp.databinding.FlowFragmentSignBinding
import com.gotov.getmeapp.ui.utils.BaseFlowFragment
import com.gotov.getmeapp.ui.utils.setToolbar

class SignFlowFragment : BaseFlowFragment(
    R.layout.flow_fragment_sign, R.id.nav_host_fragment_sign
) {
    private val binding by viewBinding(FlowFragmentSignBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setToolbar(navController, binding.signToolbar)
    }

}