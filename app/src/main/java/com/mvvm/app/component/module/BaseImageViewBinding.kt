package com.mvvm.app.component.module

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mvvm.app.component.implementaion.IImageViewBinding


class BaseImageViewBinding : IImageViewBinding {


    fun getImgFromDrawable(mContext: Context, filename: String): Int {
        var placeholder = getResourceIdentifier(mContext, filename)
        return placeholder
    }

    private fun getResourceIdentifier(mContext: Context, filename: String): Int {
        val res = mContext.resources
        return res.getIdentifier(filename, "drawable", mContext.packageName)
    }

    override fun setImageFromUrl(imageView: ImageView, filePath: String?) {
        val option: RequestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(getImgFromDrawable(imageView.context, "placeholder_logo"))
            .fitCenter()

        Glide.with(imageView.context)
            .load(filePath)
            .apply(option)
            .into(imageView)
    }
}