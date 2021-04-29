package com.baytrendy.shopifyapp.checkoutsection.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.baytrendy.shopifyapp.MyApplication
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity
import com.baytrendy.shopifyapp.databinding.ActivityOrderSuccessBinding
import com.baytrendy.shopifyapp.homesection.activities.HomePage
import com.baytrendy.shopifyapp.utils.Constant

class OrderSuccessActivity : NewBaseActivity() {
    lateinit var binding: ActivityOrderSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = findViewById<ViewGroup>(R.id.container)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_order_success, content, true)
        (application as MyApplication).mageNativeAppComponent!!.orderSuccessInjection(this)
        showTittle(resources.getString(R.string.checkout))
        showBackButton()
        binding.continueShopping.setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
            Constant.activityTransition(this)
            finish()
        }
    }
}