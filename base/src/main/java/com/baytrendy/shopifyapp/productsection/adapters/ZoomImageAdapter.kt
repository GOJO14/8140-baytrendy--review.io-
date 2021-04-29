package com.baytrendy.shopifyapp.productsection.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.models.CommanModel
import com.baytrendy.shopifyapp.databinding.ZoomImageItemBinding
import javax.inject.Inject

class ZoomImageAdapter @Inject constructor() : PagerAdapter() {
    var imageList: ArrayList<String>? = null

    fun setData(imageList: ArrayList<String>?) {
        this.imageList = imageList
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var binding = DataBindingUtil.inflate<ZoomImageItemBinding>(LayoutInflater.from(container.context), R.layout.zoom_image_item, container, false)
        val model = CommanModel()
        model.imageurl = imageList?.get(position)
        binding.commondata = model
        container.addView(binding.root)
        return binding.root
    }

    override fun getCount(): Int {
        return imageList?.size!!
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}