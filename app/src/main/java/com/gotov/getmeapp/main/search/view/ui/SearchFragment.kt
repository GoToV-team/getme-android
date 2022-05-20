package com.gotov.getmeapp.main.search.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gotov.getmeapp.R
import com.gotov.getmeapp.main.profile.model.data.User
import com.gotov.getmeapp.ui.items.UsersViewAdapter
import com.gotov.getmeapp.databinding.FragmentSearchBinding
import com.gotov.getmeapp.main.profile.model.data.getUsers

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val test: Array<User> = getUsers()

        val rec: RecyclerView = binding.userList
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        rec.layoutManager = layoutManager
        rec.adapter = UsersViewAdapter(test)
    }
}
