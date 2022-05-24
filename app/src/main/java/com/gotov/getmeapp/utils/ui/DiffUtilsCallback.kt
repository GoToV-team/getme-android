package com.gotov.getmeapp.utils.ui

import androidx.recyclerview.widget.DiffUtil

class DiffUtilsCallback<T>(
    oldList: List<T>,
    newList: List<T>,
    isItemSame: (oldItem: T, newItem: T) -> Boolean,
    isContentSame: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.Callback() {
    private val oldList: List<T>
    private val newList: List<T>
    private val isItemSame: (oldTime: T, newItem: T) -> Boolean
    private val isContentSame: (oldTime: T, newItem: T) -> Boolean

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: T = oldList[oldItemPosition]
        val newItem: T = newList[newItemPosition]
        return isItemSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: T = oldList[oldItemPosition]
        val newItem: T = newList[newItemPosition]
        return isContentSame(oldItem, newItem)
    }

    init {
        this.oldList = oldList
        this.newList = newList
        this.isItemSame = isItemSame
        this.isContentSame = isContentSame
    }
}
