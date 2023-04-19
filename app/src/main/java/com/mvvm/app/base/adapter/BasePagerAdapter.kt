package com.mvvm.app.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter


class BasePagerAdapter<T, V : ViewDataBinding>(
    @LayoutRes
    private val layoutResourceId: Int,
    private val bindVariableId: Int,
    private val items: ArrayList<T>,
    private val onDataBindCallback: OnDataBindCallback<V>) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(collection.context)
        val holder: V = DataBindingUtil.inflate(inflater, layoutResourceId, collection, false)
        val layout = BasePagerViewHolder(holder, position, onDataBindCallback)
        layout.viewDataBinding.setVariable(bindVariableId, getItem(position))
        layout.viewDataBinding.executePendingBindings()
        collection.addView(layout.viewDataBinding.root)
        return layout.viewDataBinding.root
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    private fun getItem(position: Int): T {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    fun clearAndAddDataSet(data:List<T>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun addDataSet(data:List<T>){
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun cleatDataSet(){
        items.clear()
        notifyDataSetChanged()
    }
}