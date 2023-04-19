package com.mvvm.app.component.implementaion

import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout

interface IImageViewBinding {

    @BindingAdapter("customImageFromUrl")
    fun setImageFromUrl(imageView: ImageView, filePath: String?)

}