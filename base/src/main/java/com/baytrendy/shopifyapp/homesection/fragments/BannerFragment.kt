package com.baytrendy.shopifyapp.homesection.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.databinding.MBannerlayoutBinding
import com.baytrendy.shopifyapp.basesection.models.CommanModel
import com.baytrendy.shopifyapp.homesection.models.Home

class BannerFragment : Fragment() {
    private var binding: MBannerlayoutBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.m_bannerlayout, null, false)
        val model = CommanModel()
        Log.i("MageNative-Banner","Banner"+arguments!!.getString("banner_image")!!)
        model.imageurl = arguments!!.getString("banner_image")!!
        val home = Home()
        Log.i("MageNative-Banner","id"+arguments!!.getString("id")!!)
        home.id = arguments!!.getString("id")
        Log.i("MageNative-Banner","link_to"+arguments!!.getString("link_to")!!)
        home.link_to = arguments!!.getString("link_to")
        binding!!.common = model
        binding!!.home = home
        return binding!!.root
    }


}