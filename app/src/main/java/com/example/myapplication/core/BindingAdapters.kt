package com.example.myapplication.core

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageSource")
fun setImageUrl(imageView: ImageView, imageSource: String?) {

    imageSource?.let {
        Glide.with(imageView.context)
            .load(imageSource)
            .apply(
                RequestOptions()
            )
            .into(imageView)
    }
}