package com.mvvm.app.base.adapter

import androidx.databinding.ViewDataBinding
import android.view.View

class BasePagerViewHolder<V : ViewDataBinding>(val viewDataBinding: V, private val position : Int,private val onDataBindCallback: OnDataBindCallback<V>) :
    View.OnClickListener {

    init {
        onDataBindCallback.onDataBind(viewDataBinding, this)
        viewDataBinding.root.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        onDataBindCallback.onItemClick(view, position, viewDataBinding)
    }

}