package com.baytrendy.shopifyapp.ordersection.models

import android.content.Intent
import android.util.Log
import android.view.View

import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.basesection.activities.Weblink
import com.baytrendy.shopifyapp.utils.Constant

class Order {
    var ordernumber: String? = null
    var name: String? = null
    var date: String? = null
    var price: String? = null
    var status: String? = null
    var boughtfor: String? = null
    var orderEdge: Storefront.Order? = null

    fun orderView(view: View, order: Order) {
        Log.i("MageNative", "" + order.orderEdge!!.customerUrl)
        Log.i("MageNative", "" + order.orderEdge!!.statusUrl)
        val intent = Intent(view.context, Weblink::class.java)
        intent.putExtra("name", order.name)
        intent.putExtra("link", order.orderEdge!!.statusUrl)
        view.context.startActivity(intent)
        Constant.activityTransition(view.context)
    }
}
