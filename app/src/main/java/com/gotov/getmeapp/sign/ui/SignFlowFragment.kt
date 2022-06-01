package com.gotov.getmeapp.sign.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.databinding.FlowFragmentSignBinding
import com.gotov.getmeapp.utils.ui.BaseFlowFragment
import com.gotov.getmeapp.utils.ui.setToolbar

class SignFlowFragment : BaseFlowFragment(
    R.layout.flow_fragment_sign,
    R.id.nav_host_fragment_sign
) {
    private val binding by viewBinding(FlowFragmentSignBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setToolbar(navController, binding.signToolbar)
    }
}
