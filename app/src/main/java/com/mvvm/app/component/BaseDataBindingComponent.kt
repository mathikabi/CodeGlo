package com.mvvm.app.component

import androidx.databinding.DataBindingComponent
import com.mvvm.app.component.implementaion.*
import com.mvvm.app.component.module.*

class BaseDataBindingComponent : DataBindingComponent {

    override fun getIImageViewBinding(): IImageViewBinding {
        return BaseImageViewBinding()
    }

}