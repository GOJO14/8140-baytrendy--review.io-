package com.baytrendy.shopifyapp.productsection.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.baytrendy.shopifyapp.MyApplication
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity
import com.baytrendy.shopifyapp.cartsection.activities.CartList
import com.baytrendy.shopifyapp.databinding.ActivityZoomBinding
import com.baytrendy.shopifyapp.productsection.adapters.ZoomImageAdapter
import com.baytrendy.shopifyapp.utils.Constant
import kotlinx.android.synthetic.main.m_newbaseactivity.*
import javax.inject.Inject

class ZoomActivity : NewBaseActivity() {
    private var binding: ActivityZoomBinding? = null
    private var images_list: ArrayList<String>? = null

    @Inject
    lateinit var zoomImageAdapter: ZoomImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val group = findViewById<ViewGroup>(R.id.container)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_zoom, group, true)
        (application as MyApplication).mageNativeAppComponent!!.doZoomActivityInjection(this)
        showBackButton()
        nav_view.visibility = View.GONE
        showTittle(" ")
        images_list = ArrayList<String>()
        if (intent.hasExtra("images")) {
            images_list = intent.getStringArrayListExtra("images")
            zoomImageAdapter.setData(images_list)
            binding?.imagesSlider?.adapter = zoomImageAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.m_product, menu)
        val item = menu.findItem(R.id.cart_item)
        item.setActionView(R.layout.m_count)
        val notifCount = item.actionView
        val textView = notifCount.findViewById<TextView>(R.id.count)
        textView.text = "" + cartCount
        notifCount.setOnClickListener {
            val mycartlist = Intent(this, CartList::class.java)
            startActivity(mycartlist)
            Constant.activityTransition(this)
        }
        return true
    }
}