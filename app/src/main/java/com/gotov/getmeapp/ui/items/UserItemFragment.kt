package com.gotov.getmeapp.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.gotov.getmeapp.R
import com.gotov.getmeapp.data.User

class UsersViewAdapter(users : Array<User>) : RecyclerView.Adapter<UserItemFragment>() {

    private val _users : Array<User> = users

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemFragment {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_info, parent, false)
        return UserItemFragment(view)
    }

    override fun onBindViewHolder(holder: UserItemFragment, position: Int) {
        val user = _users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return _users.size
    }

}

class UserItemFragment(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val _name: TextView = itemView.findViewById(R.id.mentor_info__name)
    private val _about: TextView = itemView.findViewById(R.id.mentor_info__description)
    private val _skills: ChipGroup = itemView.findViewById(R.id.mentor_info__skills)

    fun bind(user: User) {
        user.addToViews(_name, _about, _skills, itemView.context)

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
