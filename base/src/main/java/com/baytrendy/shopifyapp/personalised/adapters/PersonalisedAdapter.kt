package com.baytrendy.shopifyapp.personalised.adapters

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.models.CommanModel
import com.baytrendy.shopifyapp.basesection.models.ListData
import com.baytrendy.shopifyapp.databinding.MPersonalisedBinding
import com.baytrendy.shopifyapp.productsection.adapters.ProductRecylerAdapter
import com.baytrendy.shopifyapp.productsection.viewholders.ProductItem
import com.baytrendy.shopifyapp.utils.CurrencyFormatter
import java.math.BigDecimal
import javax.inject.Inject

class PersonalisedAdapter @Inject
constructor() : RecyclerView.Adapter<ProductItem>() {
    private var layoutInflater: LayoutInflater? = null
    lateinit var products: List<Storefront.Product>
    var presentmentcurrency: String? = null
    fun setData(products: List<Storefront.Product>) {
        this.products = products
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItem {
        val binding = DataBindingUtil.inflate<MPersonalisedBinding>(LayoutInflater.from(parent.context), R.layout.m_personalised, parent, false)
        return ProductItem(binding)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ProductItem, position: Int) {

        try {
            var pro = products.get(position)
            val variant = pro.variants.edges[0].node
            val data = ListData()
            Log.i("MageNative", "Product ID" + pro.id)
            data.product = pro
            data.textdata = pro.title
            data.description = pro.description
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
                    holder.personalbinding!!.regularprice.paintFlags = holder.personalbinding!!.regularprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    holder.personalbinding!!.specialprice.visibility = View.VISIBLE
                    //holder.personalbinding!!.offertext.visibility = View.VISIBLE
                    //holder.personalbinding!!.offertext.setTextColor(holder.personalbinding!!.offertext.context!!.resources.getColor(R.color.green))
                } else {
                    holder.personalbinding!!.specialprice.visibility = View.GONE
                    //holder.personalbinding!!.offertext.visibility = View.GONE
                    holder.personalbinding!!.regularprice.paintFlags = holder.personalbinding!!.regularprice.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
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
                    holder.personalbinding!!.regularprice.paintFlags = holder.personalbinding!!.regularprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    holder.personalbinding!!.specialprice.visibility = View.VISIBLE
                    //    holder.personalbinding!!.offertext.visibility = View.VISIBLE
                    //   holder.personalbinding!!.offertext.setTextColor(holder.personalbinding!!.offertext.context!!.resources.getColor(R.color.green))
                } else {
                    holder.personalbinding!!.specialprice.visibility = View.GONE
                    // holder.personalbinding!!.offertext.visibility = View.GONE
                    holder.personalbinding!!.regularprice.paintFlags = holder.personalbinding!!.regularprice.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
            holder.personalbinding!!.listdata = data
            val model = CommanModel()
            model.imageurl = pro?.images?.edges?.get(0)?.node?.transformedSrc
            holder.personalbinding!!.commondata = model
            holder.personalbinding!!.clickproduct = ProductRecylerAdapter().Product(position)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun getDiscount(regular: Double, special: Double): Int {
        return ((regular - special) / regular * 100).toInt()
    }

}
