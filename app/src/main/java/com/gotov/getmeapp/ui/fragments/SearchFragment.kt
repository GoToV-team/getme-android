package com.gotov.getmeapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gotov.getmeapp.data.User
import com.gotov.getmeapp.ui.items.UsersViewAdapter
import com.gotov.getmeapp.databinding.FragmentSearchBinding
import com.gotov.getmeapp.data.getUsers

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val test : Array<User> = getUsers()
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val rec : RecyclerView = binding.userList
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        rec.layoutManager = layoutManager
        rec.adapter = UsersViewAdapter(test)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
