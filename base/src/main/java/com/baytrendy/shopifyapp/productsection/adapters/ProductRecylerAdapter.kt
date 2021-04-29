package com.baytrendy.shopifyapp.productsection.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.baytrendy.shopifyapp.databinding.MProductitemBinding
import androidx.recyclerview.widget.RecyclerView
import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.models.CommanModel
import com.baytrendy.shopifyapp.basesection.models.ListData
import com.baytrendy.shopifyapp.basesection.viewmodels.SplashViewModel
import com.baytrendy.shopifyapp.productsection.activities.ProductView
import com.baytrendy.shopifyapp.productsection.viewholders.ProductItem
import com.baytrendy.shopifyapp.quickadd_section.activities.QuickAddActivity
import com.baytrendy.shopifyapp.repositories.Repository
import com.baytrendy.shopifyapp.utils.Constant
import com.baytrendy.shopifyapp.utils.CurrencyFormatter
import java.math.BigDecimal
import javax.inject.Inject

class ProductRecylerAdapter @Inject
constructor() : RecyclerView.Adapter<ProductItem>() {
    private var layoutInflater: LayoutInflater? = null
    lateinit var products: MutableList<Storefront.ProductEdge>
    private var activity: Activity? = null
    private var repository: Repository? = null
    var presentmentcurrency: String? = null
    fun setData(products: List<Storefront.ProductEdge>?, activity: Activity, repository: Repository) {
        this.products = products as MutableList<Storefront.ProductEdge>
        this.activity = activity
        this.repository = repository
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int {
        var viewtype = 0
        if (!products[position].node.availableForSale) {
            viewtype = -1
        }
        return viewtype
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItem {
        val binding = DataBindingUtil.inflate<MProductitemBinding>(LayoutInflater.from(parent.context), R.layout.m_productitem, parent, false)
        return ProductItem(binding)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ProductItem, position: Int) {
        val variant = this.products[position].node.variants.edges[0].node
        val data = ListData()
        Log.i("MageNative", "Product ID" + this.products[position].node.id)
        data.product = this.products[position].node
        data.textdata = this.products[position].node.title
        data.description = this.products[position].node.description
        if (presentmentcurrency == "nopresentmentcurrency") {
            data.regularprice = CurrencyFormatter.setsymbol(variant.priceV2.amount, variant.priceV2.currencyCode.toString())
            if (variant.compareAtPriceV2 != null) {
                val special = java.lang.Double.valueOf(variant.compareAtPriceV2.amount)
                val regular = java.lang.Double.valueOf(variant.priceV2.amount)
                if (BigDecimal.valueOf(special).compareTo(BigDecimal.valueOf(regular)) == 1) {
                    data.regularprice = CurrencyFormatter.setsymbol(variant.compareAtPriceV2.amount, variant.compareAtPriceV2.currencyCode.toString())
                    data.specialprice = CurrencyFormatter.setsymbol(variant.priceV2.amount, variant.priceV2.currencyCode.toString())
                    data.offertext = getDiscount(special, regular).toString() + "%off"

                } else {
                    data.regularprice = CurrencyFormatter.setsymbol(variant.priceV2.amount, variant.priceV2.currencyCode.toString())
                    data.specialprice = CurrencyFormatter.setsymbol(variant.compareAtPriceV2.amount, variant.compareAtPriceV2.currencyCode.toString())
                    data.offertext = getDiscount(regular, special).toString() + "%off"
                }
                holder.binding!!.regularprice.paintFlags = holder.binding!!.regularprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.binding!!.specialprice.visibility = View.VISIBLE
                holder.binding!!.offertext.visibility = View.VISIBLE
                holder.binding!!.offertext.setTextColor(activity!!.resources.getColor(R.color.green))
            } else {
                holder.binding!!.specialprice.visibility = View.GONE
                holder.binding!!.offertext.visibility = View.GONE
                holder.binding!!.regularprice.paintFlags = holder.binding!!.regularprice.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        } else {
            val edge = variant.presentmentPrices.edges[0]
            data.regularprice = CurrencyFormatter.setsymbol(edge!!.node.price.amount, edge.node.price.currencyCode.toString())
            if (variant.compareAtPriceV2 != null) {
                val special = java.lang.Double.valueOf(edge.node.compareAtPrice.amount)
                val regular = java.lang.Double.valueOf(edge.node.price.amount)
                if (BigDecimal.valueOf(special).compareTo(BigDecimal.valueOf(regular)) == 1) {
                    data.regularprice = CurrencyFormatter.setsymbol(edge.node.compareAtPrice.amount, edge.node.compareAtPrice.currencyCode.toString())
                    data.specialprice = CurrencyFormatter.setsymbol(edge.node.price.amount, edge.node.price.currencyCode.toString())
                    data.offertext = getDiscount(special, regular).toString() + "%off"

                } else {
                    data.regularprice = CurrencyFormatter.setsymbol(edge.node.price.amount, edge.node.price.currencyCode.toString())
                    data.specialprice = CurrencyFormatter.setsymbol(edge.node.compareAtPrice.amount, edge.node.compareAtPrice.currencyCode.toString())
                    data.offertext = getDiscount(regular, special).toString() + "%off"
                }
                holder.binding!!.regularprice.paintFlags = holder.binding!!.regularprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.binding!!.specialprice.visibility = View.VISIBLE
                holder.binding!!.offertext.visibility = View.VISIBLE
                holder.binding!!.offertext.setTextColor(activity!!.resources.getColor(R.color.green))
            } else {
                holder.binding!!.specialprice.visibility = View.GONE
                holder.binding!!.offertext.visibility = View.GONE
                holder.binding!!.regularprice.paintFlags = holder.binding!!.regularprice.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        if (SplashViewModel.featuresModel.outOfStock!!) {
            if (!this.products[position].node.availableForSale) {
                holder?.binding?.outOfStock?.visibility = View.VISIBLE
            } else {
                holder?.binding?.outOfStock?.visibility = View.GONE
            }
        }
        holder.binding!!.listdata = data
        val model = CommanModel()
        if (this.products[position].node.images.edges.size > 0) {
            model.imageurl = this.products[position].node.images.edges[0].node.transformedSrc
        }
        holder.binding!!.commondata = model
        holder.binding!!.clickproduct = Product(position)
        //  holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun getDiscount(regular: Double, special: Double): Int {

        return ((regular - special) / regular * 100).toInt()
    }

    inner class Product(var position: Int) {
        fun productClick(view: View, data: ListData) {
            val productintent = Intent(view.context, ProductView::class.java)
            productintent.putExtra("ID", data.product!!.id.toString())
            productintent.putExtra("tittle", data.textdata)
            productintent.putExtra("product", data.product)
            productintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            productintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            productintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            view.context.startActivity(productintent)
            Constant.activityTransition(view.context)
        }

        fun addCart(view: View, data: ListData) {
            var customQuickAddActivity = QuickAddActivity(context = activity!!, theme = R.style.WideDialogFull, product_id = data.product!!.id.toString(), repository = repository!!)
            customQuickAddActivity.show()
        }
    }
}
