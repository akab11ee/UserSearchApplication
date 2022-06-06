package com.example.usersearchapplication.utils.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * @Author: Akash Abhishek
 * @Date: 06 June 2022
 */

@BindingAdapter("load_image")
fun loadImage(view: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        // dummy img
        Glide.with(view)
            .load("https://github.githubassets.com/images/modules/open_graph/github-mark.png")
            .into(view)
    } else {
        Glide.with(view)
            .load(url)
            .into(view)
    }
}