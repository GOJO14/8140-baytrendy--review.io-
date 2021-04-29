package com.baytrendy.shopifyapp.homesection.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.JsonElement
import com.baytrendy.shopifyapp.MyApplication
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity
import com.baytrendy.shopifyapp.basesection.viewmodels.SplashViewModel.Companion.featuresModel
import com.baytrendy.shopifyapp.cartsection.activities.CartList
import com.baytrendy.shopifyapp.databinding.MHomepageModifiedBinding
import com.baytrendy.shopifyapp.databinding.MTopbarBinding
import com.baytrendy.shopifyapp.homesection.viewmodels.HomePageViewModel
import com.baytrendy.shopifyapp.personalised.adapters.PersonalisedAdapter
import com.baytrendy.shopifyapp.personalised.viewmodels.PersonalisedViewModel
import com.baytrendy.shopifyapp.searchsection.activities.AutoSearch
import com.baytrendy.shopifyapp.utils.ApiResponse
import com.baytrendy.shopifyapp.utils.Constant
import com.baytrendy.shopifyapp.utils.Status
import com.baytrendy.shopifyapp.utils.ViewModelFactory
import com.baytrendy.shopifyapp.wishlistsection.activities.WishList
import info.androidhive.fontawesome.FontTextView
import kotlinx.android.synthetic.main.m_homepage_modified.*
import kotlinx.android.synthetic.main.m_newbaseactivity.*
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class HomePage : NewBaseActivity() {
    private var binding: MHomepageModifiedBinding? = null

    @Inject
    lateinit var factory: ViewModelFactory
    private var homemodel: HomePageViewModel? = null
    lateinit var homepage: LinearLayoutCompat
    private var personamodel: PersonalisedViewModel? = null
    private var hasBanner: Boolean? = null
    private var hasFullSearch: Boolean = false

    @Inject
    lateinit var personalisedadapter: PersonalisedAdapter

    private val TAG = "HomePage"

    @Inject
    lateinit var padapter: PersonalisedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val group = findViewById<ViewGroup>(R.id.container)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.m_homepage_modified, group, true)
        homepage = binding!!.homecontainer
        (application as MyApplication).mageNativeAppComponent!!.doHomePageInjection(this)
        homemodel = ViewModelProvider(this, factory).get(HomePageViewModel::class.java)
        homemodel!!.context = this
        showHumburger()
        personamodel = ViewModelProvider(this, factory).get(PersonalisedViewModel::class.java)
        homemodel!!.getToastMessage().observe(this@HomePage, Observer<String> { consumeResponse(it) })
        homemodel!!.getHomePageData()?.observe(this@HomePage, Observer<HashMap<String, View>> { consumeResponse(it) })
        if (featuresModel.ai_product_reccomendaton) {
            if (Constant.ispersonalisedEnable) {
                homemodel!!.getApiResponse().observe(this, Observer<ApiResponse> { this.consumeResponse(it) })
                homemodel!!.getBestApiResponse().observe(this, Observer<ApiResponse> { this.consumeResponse(it) })
            }
        }
        homemodel!!.hasBannerOnTop.observe(this, Observer { this.ConsumeBanner(it) })
        homemodel!!.hasFullSearchOnTop.observe(this, Observer { this.consumeFullSearch(it) })
        scrollview.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY > oldScrollY) {
                Log.i(TAG, "Scroll DOWN")
                if (!hasBanner!! && hasFullSearch) {
                    toolbar.visibility = View.GONE
                    fullsearch.visibility = View.VISIBLE
                    fullsearch_container.visibility = View.VISIBLE
                } else if (hasBanner!! && !hasFullSearch) {
                    toolbar.visibility = View.VISIBLE
                    fullsearch.visibility = View.GONE
                    fullsearch_container.visibility = View.GONE
                } else if (hasBanner!! && hasFullSearch) {
                    toolbar.visibility = View.GONE
                    fullsearch.visibility = View.VISIBLE
                    fullsearch_container.visibility = View.VISIBLE
                } else if (!hasBanner!! && !hasFullSearch) {
                    toolbar.visibility = View.VISIBLE
                    fullsearch.visibility = View.GONE
                    fullsearch_container.visibility = View.GONE
                }
            }
            if (scrollY < oldScrollY) {
                Log.i(TAG, "Scroll UP")
            }
            if (scrollY == 0) {
                Log.i(TAG, "TOP SCROLL")
                if (hasBanner!! && hasFullSearch) {
                    toolbar.visibility = View.GONE
                    fullsearch.visibility = View.GONE
                    fullsearch_container.visibility = View.GONE
                } else if (hasBanner!! && !hasFullSearch) {
                    toolbar.visibility = View.GONE
                    fullsearch.visibility = View.GONE
                    fullsearch_container.visibility = View.GONE
                } else {
                    toolbar.visibility = View.VISIBLE
                    fullsearch.visibility = View.GONE
                    fullsearch_container.visibility = View.GONE
                }
            }
            if (scrollY <= 200) {
                if (hasBanner!! && hasFullSearch) {
                    toolbar.visibility = View.GONE
                    fullsearch.visibility = View.GONE
                    fullsearch_container.visibility = View.GONE
                } else if (hasBanner!! && !hasFullSearch) {
                    toolbar.visibility = View.GONE
                    fullsearch.visibility = View.GONE
                    fullsearch_container.visibility = View.GONE
                } else {
                    toolbar.visibility = View.VISIBLE
                    fullsearch.visibility = View.GONE
                    fullsearch_container.visibility = View.GONE
                }
            }
            if (scrollY == (v?.measuredHeight!! - v?.getChildAt(0).measuredHeight)) {
                Log.i(TAG, "BOTTOM SCROLL")
            }
        }
    }

    private fun consumeFullSearch(it: Boolean?) {
        hasFullSearch = it!!
    }

    private fun ConsumeBanner(it: Boolean?) {
        hasBanner = it
    }

    override fun onPause() {
        super.onPause()
        drawer_layout.closeDrawers()
    }

    fun setToggle(toolbar: androidx.appcompat.widget.Toolbar) {
        showHumburger()
        setSupportActionBar(toolbar)
        Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
            }
        }
        mDrawerToggle!!.syncState()
        mDrawerToggle!!.isDrawerIndicatorEnabled = true
        mDrawerToggle!!.toolbarNavigationClickListener = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.m_search, menu)
        item = menu.findItem(R.id.search_item)
        wishitem = menu.findItem(R.id.wish_item)
        cartitem = menu.findItem(R.id.cart_item)
        item?.setActionView(R.layout.m_search)
        wishitem?.setActionView(R.layout.m_wishcount)
        cartitem?.setActionView(R.layout.m_count)
        val search = item?.actionView
        search?.setOnClickListener {
            val searchpage = Intent(this, AutoSearch::class.java)
            startActivity(searchpage)
            Constant.activityTransition(this)
        }
        val notifCount = cartitem?.actionView
        textView = notifCount?.findViewById<TextView>(R.id.count)
        textView!!.text = "" + cartCount
        notifCount?.setOnClickListener {
            val mycartlist = Intent(this, CartList::class.java)
            startActivity(mycartlist)
            Constant.activityTransition(this)
        }
        val wishcount = wishitem?.actionView
        wishtextView = wishcount?.findViewById<TextView>(R.id.count)
        wishtextView!!.text = "" + leftMenuViewModel!!.wishListcount
        wishcount?.setOnClickListener {
            val wishlist = Intent(this, WishList::class.java)
            startActivity(wishlist)
            Constant.activityTransition(this)
        }
        return true
    }

    fun setHomeLogoImage(url: String, binding: MTopbarBinding) {
        if (!this.isDestroyed) {
            Log.i("MageNative", "Image URL" + url)
            Glide.with(this)
                    .load(url)
                    .thumbnail(0.5f)
                    .apply(RequestOptions().placeholder(R.drawable.image_placeholder).error(R.drawable.image_placeholder).dontTransform().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(binding.toolimage)
        }
    }

    fun setHomeWishList(visiblity: String) {
        when (visiblity) {
            "1" -> {
                wishitem?.setVisible(true)
            }
            else -> {
                wishitem?.setVisible(false)
            }
        }
    }

    fun setHomeIconColors(countback: String, counttext: String, iconcolor: String) {
        val wishview = wishitem?.actionView
        val cartview = cartitem?.actionView
        val searchview = item?.actionView
        val wishrelative = wishview?.findViewById<RelativeLayout>(R.id.back)
        val wishtext = wishview?.findViewById<TextView>(R.id.count)
        val wishicon = wishview?.findViewById<FontTextView>(R.id.cart_icon)
        val cartrelative = cartview?.findViewById<RelativeLayout>(R.id.back)
        val carttext = cartview?.findViewById<TextView>(R.id.count)
        val carticon = cartview?.findViewById<FontTextView>(R.id.cart_icon)
        wishrelative?.backgroundTintList = ColorStateList.valueOf(Color.parseColor(countback))
        cartrelative?.backgroundTintList = ColorStateList.valueOf(Color.parseColor(countback))
        wishtext?.setTextColor(Color.parseColor(counttext))
        carttext?.setTextColor(Color.parseColor(counttext))
        wishicon?.setTextColor(Color.parseColor(iconcolor))
        carticon?.setTextColor(Color.parseColor(iconcolor))
        val searchicon = searchview?.findViewById<FontTextView>(R.id.search_icon)
        searchicon?.setTextColor(Color.parseColor(iconcolor))
        mDrawerToggle!!.getDrawerArrowDrawable().setColor(Color.parseColor(iconcolor))
    }

    fun setHomeSearchOptions(searchback: String, searchtext: String, searhcborder: String, binding: MTopbarBinding) {
        var draw: GradientDrawable = binding.search.background as GradientDrawable
        draw.setColor(Color.parseColor(searchback))
        binding.search.setTextColor(Color.parseColor(searchtext))
        binding.search.setHintTextColor(Color.parseColor(searchtext))
        draw.setStroke(2, Color.parseColor(searhcborder));
    }

    fun setHomeSearchOption(type: String, placeholder: String, binding: MTopbarBinding) {
        when (type) {
            "middle-width-search" -> {
                item?.setVisible(false)
                binding.toolimage.visibility = View.GONE
                binding.searchsection.visibility = View.VISIBLE
                binding.search.text = placeholder
                binding.search.setOnClickListener {
                    val searchpage = Intent(this, AutoSearch::class.java)
                    startActivity(searchpage)
                    Constant.activityTransition(this)
                }
            }
            "full-width-search" -> {
                item?.setVisible(false)
                binding.toolimage.visibility = View.VISIBLE
                binding.searchsection.visibility = View.GONE
            }
            else -> {
                item?.setVisible(true)
                binding.toolimage.visibility = View.VISIBLE
                binding.searchsection.visibility = View.GONE
            }
        }
    }


    fun consumeResponse(data: String) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show()
    }

    fun consumeResponse(data: HashMap<String, View>) {
        //  Log.d(TAG, "onCreate: " + MagePrefs.getHomepageData())
        if (data.containsKey("top-bar_")) {
            homepage.addView(data.get("top-bar_"))
        }
        if (data.containsKey("category-circle_")) {
            homepage.addView(data.get("category-circle_"))
        }
        if (data.containsKey("banner-slider_")) {
            homepage.addView(data.get("banner-slider_"))
        }
        if (data.containsKey("product-list-slider_")) {
            homepage.addView(data.get("product-list-slider_"))
        }
        if (data.containsKey("category-square_")) {
            homepage.addView(data.get("category-square_"))
        }
        if (data.containsKey("collection-grid-layout_")) {
            homepage.addView(data.get("collection-grid-layout_"))
        }
        if (data.containsKey("standalone-banner_")) {
            homepage.addView(data.get("standalone-banner_"))
        }
        if (data.containsKey("three-product-hv-layout_")) {
            homepage.addView(data.get("three-product-hv-layout_"))
        }
        if (data.containsKey("fixed-customisable-layout_")) {
            homepage.addView(data.get("fixed-customisable-layout_"))
        }
        if (data.containsKey("collection-list-slider_")) {
            homepage.addView(data.get("collection-list-slider_"))
        }

    }

    override fun onResume() {
        super.onResume()
        homemodel!!.connectFirebaseForHomePageData(this, homepage)
        scrollview.scrollTo(0, 0)
        nav_view.menu.findItem(R.id.home_bottom).setChecked(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun consumeResponse(reponse: ApiResponse) {
        when (reponse.status) {
            Status.SUCCESS -> setPersonalisedData(reponse.data!!)
            Status.ERROR -> {
                reponse.error!!.printStackTrace()
                Toast.makeText(this, resources.getString(R.string.errorString), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setPersonalisedData(data: JsonElement) {
        try {
            val jsondata = JSONObject(data.toString())
            Log.i("MageNative", "TrendingProducts" + jsondata)
            if (jsondata.has("trending")) {
                binding!!.personalisedsection.visibility = View.VISIBLE
                setLayout(binding!!.personalised, "horizontal")
                personamodel!!.setPersonalisedData(jsondata.getJSONObject("trending").getJSONArray("products"), personalisedadapter, homemodel!!.presentmentCurrency!!, binding!!.personalised)
            }
            if (jsondata.has("bestsellers")) {
                binding!!.bestsellerpersonalisedsection.visibility = View.VISIBLE
                setLayout(binding!!.bestpersonalised, "horizontal")
                personamodel!!.setPersonalisedData(jsondata.getJSONObject("bestsellers").getJSONArray("products"), padapter, homemodel!!.presentmentCurrency!!, binding!!.bestpersonalised)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
