package com.baytrendy.shopifyapp.sharedprefsection

import android.content.Context
import android.content.SharedPreferences

object MagePrefs {
    private var context: Context? = null
    private var sharedPreference: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private val HOMEDATA = "home_data"
    private val PREF_NAME = "MagenativeShopify"
    private val HOME_PRODUCTS = "home_products"

    fun getInstance(context: Context) {
        this.context = context
        sharedPreference = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreference?.edit()
    }

    fun setHomepageData(homepage_date: String?) {
        editor?.putString(HOMEDATA, homepage_date)
        editor?.commit()
    }

    fun getHomepageData(): String? {
        return sharedPreference?.getString(HOMEDATA, null)
    }

    fun clearHomeData() {
        editor?.remove(HOMEDATA)
        editor?.commit()
    }

}