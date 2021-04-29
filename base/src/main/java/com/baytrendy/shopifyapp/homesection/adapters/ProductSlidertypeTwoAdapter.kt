package com.baytrendy.shopifyapp.homesection.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.databinding.MSlideritemoneBinding
import com.baytrendy.shopifyapp.databinding.MSlideritemtwoBinding
import com.baytrendy.shopifyapp.basesection.models.CommanModel
import com.baytrendy.shopifyapp.basesection.models.ListData
import com.baytrendy.shopifyapp.homesection.models.Product
import com.baytrendy.shopifyapp.homesection.viewholders.SliderItemTypeOne
import com.baytrendy.shopifyapp.productsection.activities.ProductView
import com.baytrendy.shopifyapp.utils.CurrencyFormatter

import java.math.BigDecimal

import javax.inject.Inject

class ProductSlidertypeTwoAdapter @Inject
 constructor() : RecyclerView.Adapter<SliderItemTypeOne>() {
    private var layoutInflater: LayoutInflater? = null
    private var products: List<Storefront.ProductEdge>? = null
    private var activity: Activity? = null
    var presentmentcurrency: String? = null
    fun setData(products: List<Storefront.ProductEdge>?, activity: Activity) {
        this.products = products
        this.activity = activity
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderItemTypeOne {
        val binding = DataBindingUtil.inflate<MSlideritemtwoBinding>(LayoutInflater.from(parent.context), R.layout.m_slideritemtwo, parent, false)
        return SliderItemTypeOne(binding)
    }

    override fun onBindViewHolder(item: SliderItemTypeOne, position: Int) {
        val variant = products?.get(position)!!.node.variants.edges[0].node
        val data = ListData()
        data.product = products?.get(position)!!.node
        item.bindingtwo.listdata = data
        val model = CommanModel()
        model.imageurl = products?.get(position)?.node?.images?.edges?.get(0)?.node?.transformedSrc
        item.bindingtwo.commondata = model
       // item.bindingtwo.clickproduct = Product()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return products!!.size
    }

    fun getDiscount(regular: Double, special: Double): Int {
        return ((regular - special) / regular * 100).toInt()
    }


}
