package com.baytrendy.shopifyapp.ordersection.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.databinding.MOrderitemBinding
import com.baytrendy.shopifyapp.ordersection.models.Order
import com.baytrendy.shopifyapp.ordersection.viewholders.OrderItem
import com.baytrendy.shopifyapp.ordersection.viewmodels.OrderListViewModel
import com.baytrendy.shopifyapp.utils.CurrencyFormatter

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import javax.inject.Inject

class OrderListAdapter @Inject
constructor() : RecyclerView.Adapter<OrderItem>() {
    var data: MutableList<Storefront.OrderEdge>? = null
    private var layoutInflater: LayoutInflater? = null
    private var model: OrderListViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItem {
        val binding = DataBindingUtil.inflate<MOrderitemBinding>(LayoutInflater.from(parent.context), R.layout.m_orderitem, parent, false)
        binding.orderdetails.textSize = 11f
        binding.orderdetails.setTextColor(binding.root.context.resources.getColor(R.color.colorPrimaryDark))
        binding.orderno.textSize = 11f
        binding.name.textSize = 11f
        binding.placedontext.textSize = 11f
        binding.date.textSize = 11f
        binding.totalspendingtext.textSize = 11f
        binding.ordernoheading.textSize = 11f
        binding.boughtforheading.textSize = 11f
        binding.boughtfor.textSize = 11f
        binding.ordernoheading.textSize = 11f
        binding.totalspending.textSize = 11f
        return OrderItem(binding)
    }

    override fun onBindViewHolder(holder: OrderItem, position: Int) {
        try {
            val order = Order()
            order.orderEdge = data?.get(position)!!.node
            order.ordernumber = data?.get(position)!!.node.orderNumber!!.toString()
            order.name = data?.get(position)!!.node.name
            val sdf2 = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val expiretime = sdf.parse(data?.get(position)!!.node.processedAt.toLocalDateTime().toString())
            val time = sdf2.format(expiretime!!)
            order.date = time
            order.price = CurrencyFormatter.setsymbol(data?.get(position)!!.node.totalPriceV2.amount, data?.get(position)!!.node.totalPriceV2.currencyCode.toString())
            order.status = data?.get(position)!!.node.statusUrl
            if (data?.get(position)!!.node.shippingAddress != null) {
                holder.binding.boughtfor.visibility = View.VISIBLE
                holder.binding.boughtforheading.visibility = View.VISIBLE
                order.boughtfor = data?.get(position)!!.node.shippingAddress.firstName + " " + data?.get(position)!!.node.shippingAddress.lastName
            } else {
                holder.binding.boughtfor.visibility = View.GONE
                holder.binding.boughtforheading.visibility = View.GONE
            }
            holder.binding.order = order
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    fun setData(data: MutableList<Storefront.OrderEdge>?, model: OrderListViewModel?) {
        this.data = data
        this.model = model
    }
}
