package com.baytrendy.shopifyapp.productsection.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.productsection.fragments.ImageFragment

class ImagSlider(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private var images: List<Storefront.ImageEdge>? = null
    private var video_thumbnail: String? = null
    private var videoLink: String? = null
    fun setData(images: List<Storefront.ImageEdge>, video_thumbnail: String? = null, videoLink: String? = null) {
        this.images = images
        this.videoLink = videoLink
        this.video_thumbnail = video_thumbnail
    }

    override fun getItem(position: Int): Fragment {
        var fragment: ImageFragment? = null
        try {
            fragment = ImageFragment(images!!)
            val bundle = Bundle()
            if (position == 1 && video_thumbnail != null) {
                bundle.putString("url", video_thumbnail)
                bundle.putString("type", "video")
                bundle.putString("video_link", videoLink)
            } else {
                bundle.putString("url", images!![position].node.originalSrc)
            }
            fragment.arguments = bundle
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return fragment!!
    }

    override fun getCount(): Int {
        return images!!.size
    }
}
