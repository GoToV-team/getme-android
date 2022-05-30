package com.gotov.getmeapp.main.plans.view.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gotov.getmeapp.R
import com.gotov.getmeapp.main.plans.model.data.Menti
import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.sign.signup.view.ui.ContinueRegisterDialogFragment

class MentisViewAdapter(
    mentis: ArrayList<Menti>,
    private val onApply: (menti: Menti) -> Unit,
    private val onCancel: (menti: Menti) -> Unit
) : RecyclerView.Adapter<MentiItemHolder>() {
    private val _mentis: ArrayList<Menti> = mentis

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentiItemHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.menti_info,
            parent,
            false
        )

        return MentiItemHolder(view, onApply, onCancel)
    }

    override fun onBindViewHolder(holder: MentiItemHolder, position: Int) {
        val menti = _mentis[position]
        holder.bind(menti)
    }

    override fun getItemCount(): Int {
        return _mentis.size
    }

    fun setData(mentis: Collection<Menti>) {
        _mentis.clear()
        _mentis.addAll(mentis)
    }

    fun getData(): List<Menti> {
        return _mentis.toList()
    }
}

class MentiItemHolder(
    itemView: View,
    private val onApply: (menti: Menti) -> Unit,
    private val onCancel: (menti: Menti) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val _name: TextView = itemView.findViewById(R.id.menti_item_name)
    private val _image: ImageView = itemView.findViewById(R.id.menti_item_avatar)
    private val _buttonApply: Button = itemView.findViewById(R.id.menti_apply_button)
    private val _buttonCancel: Button = itemView.findViewById(R.id.menti_cancel_button)

    fun bind(menti: Menti) {
        menti.addToViews(_name, null, _image)

        _buttonApply.setOnClickListener {
            onApply(menti)
        }

        _buttonCancel.setOnClickListener {
            onCancel(menti)
        }
    }
}
