package com.baytrendy.shopifyapp.utils

import android.R
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.shopify.buy3.HttpCachePolicy
import com.baytrendy.shopifyapp.productsection.models.VariantData
import java.util.concurrent.TimeUnit


object Constant {

    var ispersonalisedEnable: Boolean = false
    var locale: String = "en"
    var previous: VariantData? = null
    var current: VariantData? = null
    var policy: HttpCachePolicy.ExpirePolicy = HttpCachePolicy.Default.CACHE_FIRST.expireAfter(5, TimeUnit.SECONDS)
    fun getProgressDialog(context: Context, msg: String): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage(msg)
        progressDialog.setCancelable(false)
        return progressDialog
    }

    fun activityTransition(context: Context) {
        (context as Activity).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


    fun checkInternetConnection(context: Context): Boolean {
        val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity == null) {
            return false
        } else {
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (i in info.indices) {
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
