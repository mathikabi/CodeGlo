package com.mvvm.app.base.adapter

import androidx.databinding.ViewDataBinding
import android.view.View

class BaseViewHolder<V : ViewDataBinding>(val viewDataBinding: V,
                                          private val onDataBindCallback: OnDataBindCallback<V>
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

    init {
        onDataBindCallback.onDataBind(viewDataBinding, this)
        viewDataBinding.root.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        onDataBindCallback.onItemClick(view, adapterPosition,viewDataBinding)
    }

}