package com.baytrendy.shopifyapp.ordersection.activities

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.MyApplication
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.databinding.MOrderlistBinding
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity
import com.baytrendy.shopifyapp.ordersection.adapters.OrderListAdapter
import com.baytrendy.shopifyapp.ordersection.viewmodels.OrderListViewModel
import com.baytrendy.shopifyapp.utils.ViewModelFactory

import javax.inject.Inject

class OrderList : NewBaseActivity() {
    private var binding: MOrderlistBinding? = null

    @Inject
    lateinit var factory: ViewModelFactory
    private var orderlist: RecyclerView? = null
    private var model: OrderListViewModel? = null

    @Inject
    lateinit var adapter: OrderListAdapter
    private var orders: MutableList<Storefront.OrderEdge>? = null
    private var ordercursor: String? = null
    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = recyclerView.layoutManager!!.childCount
            val totalItemCount = recyclerView.layoutManager!!.itemCount
            val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (!recyclerView.canScrollVertically(1)) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition > 0
                        && totalItemCount >= orders!!.size) {
                    model!!.cursor = ordercursor!!
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val group = findViewById<ViewGroup>(R.id.container)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.m_orderlist, group, true)
        orderlist = setLayout(binding!!.orderlist, "vertical")
        orderlist!!.addOnScrollListener(recyclerViewOnScrollListener)
        showTittle(resources.getString(R.string.myorders))
        showBackButton()
        (application as MyApplication).mageNativeAppComponent!!.doOrderListInjection(this)
        model = ViewModelProviders.of(this, factory).get(OrderListViewModel::class.java)
        model!!.context = this
        model!!.errorResponse.observe(this, Observer<String> { this.showToast(it) })
        model!!.getResponse_().observe(this, Observer<Storefront.OrderConnection> { this.consumeResponse(it) })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun consumeResponse(response: Storefront.OrderConnection) {
        try {
            if (response.edges.size > 0) {
                if (this.orders == null) {
                    this.orders = response.edges
                    adapter!!.setData(orders as MutableList<Storefront.OrderEdge>?, model)
                    orderlist!!.adapter = adapter
                } else {
                    this.orders!!.addAll(response.edges)
                    adapter!!.notifyDataSetChanged()
                }
                ordercursor = this.orders!![this.orders!!.size - 1].cursor
                Log.i("MageNative", "Cursor : " + ordercursor!!)
            } else {
                showToast(resources.getString(R.string.noorders))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
