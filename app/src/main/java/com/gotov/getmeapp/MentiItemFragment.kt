package com.gotov.getmeapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView

class MentisViewAdapter(mentis : Array<Menti>) : RecyclerView.Adapter<MentiItemFragment>() {

    private val _mentis : Array<Menti> = mentis

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentiItemFragment {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.menti_info, parent, false)
        return MentiItemFragment(view)
    }

    override fun onBindViewHolder(holder: MentiItemFragment, position: Int) {
        val menti = _mentis[position]
        holder.bind(menti)
    }

    override fun getItemCount(): Int {
        return _mentis.size
    }

}

class MentiItemFragment(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val _name: TextView = itemView.findViewById(R.id.menti_item_name)
    private val _about: TextView = itemView.findViewById(R.id.menti_item_about)

    fun bind(menti: Menti) {
        _name.text = menti.Name
        _about.text = menti.About
    }
}
