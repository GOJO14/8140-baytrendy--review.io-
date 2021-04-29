package com.baytrendy.shopifyapp.productsection.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.shopify.buy3.Storefront

import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.databinding.MImagefragmentBinding
import com.baytrendy.shopifyapp.basesection.fragments.BaseFragment
import com.baytrendy.shopifyapp.basesection.models.CommanModel
import com.baytrendy.shopifyapp.productsection.activities.VideoPlayerActivity
import com.baytrendy.shopifyapp.productsection.activities.ZoomActivity
import com.baytrendy.shopifyapp.utils.Constant

import java.util.Objects

class ImageFragment(var images: List<Storefront.ImageEdge>) : BaseFragment(), View.OnClickListener {
    private val TAG = "ImageFragment"
    private var image_list: ArrayList<String>? = null
    private var linkType: String? = null
    private var videoLink: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<MImagefragmentBinding>(layoutInflater, R.layout.m_imagefragment, null, false)
        val url = Objects.requireNonNull<Bundle>(arguments).getString("url")
        linkType = Objects.requireNonNull<Bundle>(arguments).getString("type")
        videoLink = Objects.requireNonNull<Bundle>(arguments).getString("video_link")
        if (linkType.equals("video")) {
            binding.playButton.visibility = View.VISIBLE
        }else{
            binding.playButton.visibility = View.GONE
        }
        val model = CommanModel()
        model.imageurl = url!!
        binding.commondata = model
        binding.image.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onClick(v: View?) {
        if (linkType.equals("video")) {
            if (videoLink?.contains("youtu")!!) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoLink)))
                Constant.activityTransition(v?.context!!)
            } else {
                var intent = Intent(context, VideoPlayerActivity::class.java)
                intent.putExtra("videoLink", videoLink)
                context?.startActivity(intent)
                Constant.activityTransition(v?.context!!)
            }
        } else {
            image_list = ArrayList<String>()
            images.forEach {
                image_list?.add(it.node.originalSrc)
            }
            var intent = Intent(context, ZoomActivity::class.java)
            intent.putStringArrayListExtra("images", image_list)
            context?.startActivity(intent)
            Constant.activityTransition(v?.context!!)
        }
    }
}