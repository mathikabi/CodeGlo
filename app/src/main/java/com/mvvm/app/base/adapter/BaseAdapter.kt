package com.mvvm.app.base.adapter

import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T : RecyclerView.ViewHolder, D> : RecyclerView.Adapter<T>() {

    abstract fun setData(data: List<D>)

   // protected abstract fun getLayoutIdForPosition(position: Int): Int
}