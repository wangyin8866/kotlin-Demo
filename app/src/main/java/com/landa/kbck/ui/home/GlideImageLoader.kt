package com.landa.kbck.ui.home

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * @author wyman
 * @date  2018/9/6
 * description :
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        context?.let { imageView?.let { it1 -> Glide.with(it).load(path).into(it1)}}
    }
}