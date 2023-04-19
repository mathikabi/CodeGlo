package com.mvvm.app.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


class BaseRecyclerViewAdapter<T, V : ViewDataBinding>   (@LayoutRes
                                                     private val layoutResourceId: Int,
                                                     private val bindVariableId: Int,
                                                     private val items: ArrayList<T>,
                                                     private val onDataBindCallback: OnDataBindCallback<V>
) : RecyclerView.Adapter<BaseViewHolder<V>>()  {

    private var count: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        return BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutResourceId, parent, false), onDataBindCallback)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {

        holder.viewDataBinding.setVariable(bindVariableId, getItem(position))
        holder.viewDataBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getItem(position: Int): T {
        return items[position]
    }

    fun addDataArraySet(data: ArrayList<T>){
        if (count > 0) {
            for (i in 0 until count){
                items.add(data[i])
            }
        } else {
            items.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addDataSet(data:List<T>){
        if (count > 0) {
            for (i in 0 until count){
                items.add(data[i])
            }
        } else {
            items.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun getItems():List<T>{
        return items
    }

    fun setMaxCount(max: Int){
        count = max
    }

    fun cleatDataSet(){
        items.clear()
        notifyDataSetChanged()
    }

    fun clearAndAddDataSet(data: List<T>) {
        cleatDataSet()
        addDataSet(data)
    }

   /* override fun getFilter(): Filter? {

        *//*if (items == null) {
            valueFilter = ValueFilter()
        }
        return valueFilter*//*
    }*/

    /*private inner class ValueFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()

            if (constraint != null && constraint.length > 0) {
                val filterList = ArrayList()
                for (i in 0 until mStringFilterList.size()) {
                    if (mStringFilterList.get(i).toUpperCase().contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i))
                    }
                }
                results.count = filterList.size
                results.values = filterList
            } else {
                results.count = mStringFilterList.size()
                results.values = mStringFilterList
            }
            return results

        }

        override fun publishResults(constraint: CharSequence,
                                    results: Filter.FilterResults) {
            mData = results.values as List<String>
            notifyDataSetChanged()
        }

    }*/

}