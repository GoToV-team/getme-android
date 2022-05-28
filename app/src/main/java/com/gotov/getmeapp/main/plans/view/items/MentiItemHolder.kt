package com.gotov.getmeapp.main.plans.view.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gotov.getmeapp.R
import com.gotov.getmeapp.main.plans.model.data.Menti

class MentisViewAdapter(mentis: Array<Menti>) : RecyclerView.Adapter<MentiItemHolder>() {

    private val _mentis: Array<Menti> = mentis

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentiItemHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.menti_info,
            parent,
            false
        )
        return MentiItemHolder(view)
    }

    override fun onBindViewHolder(holder: MentiItemHolder, position: Int) {
        val menti = _mentis[position]
        holder.bind(menti)
    }

    override fun getItemCount(): Int {
        return _mentis.size
    }
}

class MentiItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val _name: TextView = itemView.findViewById(R.id.menti_item_name)
    private val _about: TextView = itemView.findViewById(R.id.menti_item_about)
    private val _buttonApply: Button = itemView.findViewById(R.id.menti_apply_button)

    fun bind(menti: Menti) {
        menti.addToViews(_name, _about)
        _buttonApply.setOnClickListener {
        }
    }
}
