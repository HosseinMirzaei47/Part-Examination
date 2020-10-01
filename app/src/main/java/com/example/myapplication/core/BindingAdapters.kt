package com.example.myapplication.core

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView

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

@BindingAdapter("backgroundColor")
fun setColor(materialCardView: View, strength: Int) {

    materialCardView as MaterialCardView

    when {
        strength < 50 -> {
            materialCardView.setBackgroundColor(Color.YELLOW)
        }
        strength in 51..299 -> {
            materialCardView.setBackgroundColor(Color.BLUE)
        }
        else -> {
            materialCardView.setBackgroundColor(Color.RED)
        }
    }

}