package com.gotov.getmeapp.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R
import com.gotov.getmeapp.main.search.model.data.User

class UsersViewAdapter(users: ArrayList<User>) : RecyclerView.Adapter<UserItemHolder>() {

    private var _users: ArrayList<User> = users

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.user_info,
            parent,
            false
        )
        return UserItemHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemHolder, position: Int) {
        val user = _users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return _users.size
    }

    fun setData(users: Array<User>) {
        _users.clear()
        _users.addAll(users)
    }

    fun getData(): List<User> {
        return _users.toList()
    }
}

class UserItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val _name: TextView = itemView.findViewById(R.id.mentor_info__name)
    private val _about: TextView = itemView.findViewById(R.id.mentor_info__description)
    private val _skills: ChipGroup = itemView.findViewById(R.id.mentor_info__skills)
    private val _image: ImageView = itemView.findViewById(R.id.mentor_info__avatar)

    fun bind(user: User) {
        user.addToViews(_name, _about, _skills, _image, itemView.context)

        var navController: NavController?
        this.apply {
            itemView.setOnClickListener {
                navController = Navigation.findNavController(itemView)
                val args: Bundle = bundleOf("user_id" to user.id)
                navController!!.navigate(R.id.action_SearchFragment_to_UserFragment, args)
            }
        }
    }
}
