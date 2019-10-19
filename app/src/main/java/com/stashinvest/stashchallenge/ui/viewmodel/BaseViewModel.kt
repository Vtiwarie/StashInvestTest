package com.stashinvest.stashchallenge.ui.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewModel<T : RecyclerView.ViewHolder>(private val itemResourceId: Int) {
    
    abstract val viewType: ViewModelType
    
    fun createViewHolder(parent: ViewGroup): T {
        val view = LayoutInflater.from(parent.context).inflate(itemResourceId, parent, false)
        return createItemViewHolder(view)
    }
    
    abstract fun createItemViewHolder(view: View): T
    
    @Suppress("UNCHECKED_CAST")
    fun bindViewHolder(holder: RecyclerView.ViewHolder) {
        bindItemViewHolder(holder as T)
    }
    
    abstract fun bindItemViewHolder(holder: T)
}
