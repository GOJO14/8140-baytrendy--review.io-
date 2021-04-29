package com.baytrendy.shopifyapp.utils

import android.util.Log
import com.baytrendy.shopifyapp.MyApplication
import com.baytrendy.shopifyapp.repositories.Repository
import com.baytrendy.shopifyapp.sharedprefsection.MagePrefs
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import javax.inject.Inject

class Urls {
    @Inject
    lateinit var repository: Repository
    var app: MyApplication

    constructor(app: MyApplication) {
        this.app = app
        app.mageNativeAppComponent!!.doURlInjection(this)
    }

    companion object Data {
        const val BASE_URL: String = "https://shopifymobileapp.cedcommerce.com/"//put your base url here
        const val PERSONALISED: String = "https://recommendations.loopclub.io/api/v1/"//put your base url here
        const val MENU: String = "shopifymobile/shopifyapi/getcategorymenus/"//put your end point here
        const val SETORDER: String = "shopifymobile/shopifyapi/setorder"
        const val SETDEVICES: String = "shopifymobile/shopifyapi/setdevices"
        const val RECOMMENDATION: String = "recommendations/"
        const val HEADER: String = "Domain-Name: douban"
        const val HOMEPAGE: String = "shopifymobile/shopifyapi/homepagedata"
        const val CLIENT: String = "magenative"
        const val TOKEN: String = "a2ds21R!3rT#R@R23r@#3f3ef"
        const val MulipassSecret: String = "1f4237c87f31090e5763feaa34962b72"
        const val SIZECHART: String = "https://app.kiwisizing.com/size"
    }

    val shopdomain: String
        get() {
            var domain = "outdoor-i.myshopify.com" //magenative-store.myshopify.com
            try {
                val executor = Executors.newSingleThreadExecutor()
                val callable = Callable {
                    if (repository.getPreviewData().size > 0) {
                        domain = repository.getPreviewData().get(0).shopurl!!
                    }
                    domain
                }
                val future = executor.submit(callable)
                domain = future.get()
                executor.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Log.i("MageNative", "domain" + domain)
            return domain
        }
    val mid: String
        get() {
            var domain = "8140" // 3937
            try {
                val executor = Executors.newSingleThreadExecutor()
                val callable = Callable {
                    if (repository.getPreviewData().size > 0) {
                        domain = repository.getPreviewData().get(0).mid!!
                    }
                    domain
                }
                val future = executor.submit(callable)
                domain = future.get()
                executor.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Log.i("MageNative", "domain" + domain)
            return domain
        }
    val apikey: String
        get() {
            var key = "4acc28956b4db2c1f16963ab3bf39689" //63893d2330e639632e2eab540e9d2d75
            try {
                val executor = Executors.newSingleThreadExecutor()
                val callable = Callable {
                    if (repository.getPreviewData().size > 0) {
                        key = repository.getPreviewData().get(0).apikey!!
                    }
                    key
                }
                val future = executor.submit(callable)
                key = future.get()
                executor.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Log.i("MageNative", "domain" + key)
            return key
        }
}
