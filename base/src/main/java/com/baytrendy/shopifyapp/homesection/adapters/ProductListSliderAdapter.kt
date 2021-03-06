package com.baytrendy.shopifyapp.homesection.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.databinding.MCustomisableListBinding
import com.baytrendy.shopifyapp.basesection.models.CommanModel
import com.baytrendy.shopifyapp.basesection.models.ListData
import com.baytrendy.shopifyapp.basesection.viewmodels.SplashViewModel
import com.baytrendy.shopifyapp.homesection.viewholders.SliderItemTypeOne
import com.baytrendy.shopifyapp.productsection.activities.ProductView
import com.baytrendy.shopifyapp.utils.CurrencyFormatter
import org.json.JSONObject
import java.math.BigDecimal
import javax.inject.Inject

class

ProductListSliderAdapter @Inject
constructor() : RecyclerView.Adapter<SliderItemTypeOne>() {
    private var layoutInflater: LayoutInflater? = null
    private var products: List<Storefront.Product>? = null
    private var activity: Activity? = null
    var presentmentcurrency: String? = null
    var jsonObject: JSONObject? = null
    fun setData(products: List<Storefront.Product>?, activity: Activity, jsonObject: JSONObject) {
        this.products = products
        this.activity = activity
        this.jsonObject = jsonObject
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderItemTypeOne {
        var binding = DataBindingUtil.inflate<MCustomisableListBinding>(LayoutInflater.from(parent.context), R.layout.m_customisable_list, parent, false)
        if (jsonObject!!.getString("item_shape").equals("square")) {
            binding.card.cardElevation = 0f
            binding.card.radius = 0f
            binding.card.useCompatPadding = false
        }
        var params = binding.pricesection.layoutParams as ConstraintLayout.LayoutParams
        var alignment: String
        if (jsonObject!!.has("item_text_alignment")) {
            alignment = jsonObject!!.getString("item_text_alignment")
        } else {
            alignment = jsonObject!!.getString("item_alignment")
        }
        when (alignment) {
            "right" -> {
                binding.name.gravity = Gravity.END
                params.startToStart = ConstraintSet.GONE
                params.endToEnd = ConstraintSet.PARENT_ID
            }
            "left" -> {
                binding.name.gravity = Gravity.START
                params.startToStart = ConstraintSet.PARENT_ID
                params.endToEnd = ConstraintSet.GONE
            }
        }
        var tittlevisibility: Int = View.GONE
        if (jsonObject!!.getString("item_title").equals("1")) {
            var item_title_color = JSONObject(jsonObject!!.getString("item_title_color"))
            binding.name.setTextColor(Color.parseColor(item_title_color.getString("color")))
            tittlevisibility = View.VISIBLE
        } else {
            tittlevisibility = View.GONE
        }
        var productpricevisibility: Int = View.GONE
        if (jsonObject!!.getString("item_price").equals("1")) {
            var item_price_color = JSONObject(jsonObject!!.getString("item_price_color"))
            binding.regularprice.setTextColor(Color.parseColor(item_price_color.getString("color")))
            productpricevisibility = View.VISIBLE
        } else {
            productpricevisibility = View.GONE
        }
        var specialpricevisibility: Int = View.GONE
        if (jsonObject!!.getString("item_compare_at_price").equals("1")) {
            var item_compare_at_price_color = JSONObject(jsonObject!!.getString("item_compare_at_price_color"))
            binding.specialprice.setTextColor(Color.parseColor(item_compare_at_price_color.getString("color")))
            specialpricevisibility = View.VISIBLE
        } else {
            specialpricevisibility = View.GONE
        }
        var cell_background_color = JSONObject(jsonObject!!.getString("cell_background_color"))
        binding.name.visibility = tittlevisibility
        binding.regularprice.visibility = productpricevisibility
        binding.specialprice.visibility = specialpricevisibility
        if (jsonObject!!.getString("item_border").equals("1")) {
            if (jsonObject!!.getString("item_shape").equals("square")) {
                var drawable = GradientDrawable()
                var background = JSONObject(jsonObject!!.getString("item_border_color"))
                drawable.shape = GradientDrawable.RECTANGLE
                drawable.setColor(Color.parseColor(cell_background_color.getString("color")))
                drawable.setStroke(1, Color.parseColor(background.getString("color")))
                binding.main.background = drawable
            } else {
                binding.main.setBackgroundColor(Color.parseColor(cell_background_color.getString("color")))
            }
        }
        val face: Typeface
        when (jsonObject!!.getString("item_title_font_weight")) {
            "bold" -> {
                face = Typeface.createFromAsset(activity!!.assets, "fonts/bold.ttf");
            }
            else -> {
                face = Typeface.createFromAsset(activity!!.assets, "fonts/normal.ttf");
            }
        }
        binding.name.setTypeface(face)
        if (jsonObject!!.getString("item_title_font_style").equals("italic")) {
            binding.name.setTypeface(binding.name.getTypeface(), Typeface.ITALIC);
        }
        val priceface: Typeface
        when (jsonObject!!.getString("header_subtitle_font_weight")) {
            "bold" -> {
                priceface = Typeface.createFromAsset(activity!!.assets, "fonts/bold.ttf");
            }
            else -> {
                priceface = Typeface.createFromAsset(activity!!.assets, "fonts/normal.ttf");
            }
        }
        binding.regularprice.setTypeface(priceface)
        if (jsonObject!!.getString("item_price_font_style").equals("italic")) {
            binding.regularprice.setTypeface(binding.regularprice.getTypeface(), Typeface.ITALIC);
        }
        val specialpriceface: Typeface
        when (jsonObject!!.getString("item_compare_at_price_font_weight")) {
            "bold" -> {
                specialpriceface = Typeface.createFromAsset(activity!!.assets, "fonts/bold.ttf");
            }
            else -> {
                specialpriceface = Typeface.createFromAsset(activity!!.assets, "fonts/normal.ttf");
            }
        }
        binding.specialprice.setTypeface(specialpriceface)
        if (jsonObject!!.getString("item_compare_at_price_font_style").equals("italic")) {
            binding.specialprice.setTypeface(binding.specialprice.getTypeface(), Typeface.ITALIC);
        }
        return SliderItemTypeOne(binding)
    }

    override fun onBindViewHolder(item: SliderItemTypeOne, position: Int) {
        val variant = products?.get(position)!!.variants.edges.get(0).node
        val data = ListData()
        data.product = products?.get(position)
        data.textdata = products?.get(position)?.title.toString().trim()
        if (presentmentcurrency == "nopresentmentcurrency") {
            data.regularprice = CurrencyFormatter.setsymbol(variant!!.priceV2.amount, variant.priceV2.currencyCode.toString())
            if (variant.compareAtPriceV2 != null) {
                val special = java.lang.Double.valueOf(variant.compareAtPriceV2.amount)
                val regular = java.lang.Double.valueOf(variant.priceV2.amount)
                if (BigDecimal.valueOf(special).compareTo(BigDecimal.valueOf(regular)) == 1) {
                    data.regularprice = CurrencyFormatter.setsymbol(variant.compareAtPriceV2.amount, variant.compareAtPriceV2.currencyCode.toString())
                    data.specialprice = CurrencyFormatter.setsymbol(variant.priceV2.amount, variant.priceV2.currencyCode.toString())

                } else {
                    data.regularprice = CurrencyFormatter.setsymbol(variant.priceV2.amount, variant.priceV2.currencyCode.toString())
                    data.specialprice = CurrencyFormatter.setsymbol(variant.compareAtPriceV2.amount, variant.compareAtPriceV2.currencyCode.toString())

                }
                item.listbinding.regularprice.paintFlags = item.listbinding.regularprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                item.listbinding.specialprice.visibility = View.VISIBLE
            } else {
                item.listbinding.regularprice.paintFlags = item.listbinding.regularprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                item.listbinding.specialprice.visibility = View.GONE
            }
        } else {
            val edge = variant!!.presentmentPrices.edges[0]
            data.regularprice = CurrencyFormatter.setsymbol(edge!!.node.price.amount, edge.node.price.currencyCode.toString())
            if (variant.compareAtPriceV2 != null) {
                val special = java.lang.Double.valueOf(edge.node.compareAtPrice.amount)
                val regular = java.lang.Double.valueOf(edge.node.price.amount)
                if (BigDecimal.valueOf(special).compareTo(BigDecimal.valueOf(regular)) == 1) {
                    data.regularprice = CurrencyFormatter.setsymbol(edge.node.compareAtPrice.amount, edge.node.compareAtPrice.currencyCode.toString())
                    data.specialprice = CurrencyFormatter.setsymbol(edge.node.price.amount, edge.node.price.currencyCode.toString())


                } else {
                    data.regularprice = CurrencyFormatter.setsymbol(edge.node.price.amount, edge.node.price.currencyCode.toString())
                    data.specialprice = CurrencyFormatter.setsymbol(edge.node.compareAtPrice.amount, edge.node.compareAtPrice.currencyCode.toString())
                }
                item.listbinding.regularprice.paintFlags = item.listbinding.regularprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                item.listbinding.specialprice.visibility = View.VISIBLE
            } else {
                item.listbinding.specialprice.visibility = View.GONE
                item.listbinding.regularprice.paintFlags = item.listbinding.regularprice.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
        if (SplashViewModel.featuresModel.outOfStock!!) {
            if (!products?.get(position)!!.availableForSale) {
                item?.listbinding?.outOfStock?.visibility = View.VISIBLE
            } else {
                item?.listbinding?.outOfStock?.visibility = View.GONE
            }
        }

        val model = CommanModel()
        model.imageurl = products?.get(position)?.images?.edges?.get(0)?.node?.transformedSrc
        item.listbinding.listdata = data
        item.listbinding.commondata = model
        item.listbinding.clickproduct = ProductListAdapter().Product()

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return products!!.size
    }

}
