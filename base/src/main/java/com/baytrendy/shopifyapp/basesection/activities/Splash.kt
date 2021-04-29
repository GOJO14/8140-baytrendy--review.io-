package com.baytrendy.shopifyapp.basesection.activities

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.ktx.Firebase
import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.MyApplication
import com.baytrendy.shopifyapp.MyApplication.Companion.context
import com.baytrendy.shopifyapp.MyApplication.Companion.dataBaseReference
import com.baytrendy.shopifyapp.MyApplication.Companion.firebaseapp
import com.baytrendy.shopifyapp.MyApplication.Companion.getmFirebaseSecondanyInstance
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.databinding.MSplashBinding
import com.baytrendy.shopifyapp.basesection.models.CommanModel
import com.baytrendy.shopifyapp.basesection.viewmodels.SplashViewModel
import com.baytrendy.shopifyapp.basesection.viewmodels.SplashViewModel.Companion.featuresModel
import com.baytrendy.shopifyapp.dbconnection.entities.AppLocalData
import com.baytrendy.shopifyapp.homesection.activities.HomePage
import com.baytrendy.shopifyapp.productsection.activities.ProductView
import com.baytrendy.shopifyapp.trialsection.activities.TrialExpired
import com.baytrendy.shopifyapp.utils.*
import javax.inject.Inject

class Splash : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var splashmodel: SplashViewModel? = null
    private var binding: MSplashBinding? = null
    private var product_id: String? = null
    private var tm: JobScheduler? = null
    private lateinit var auth: FirebaseAuth
    private var productTitle: String? = null
    private var productData: Storefront.Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.m_splash, null, false)
        setContentView(binding!!.root)
        (application as MyApplication).mageNativeAppComponent!!.doSplashInjection(this)
        splashmodel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
        splashmodel!!.message.observe(this, Observer<String> { this.showToast(it) })
        splashmodel?.setPresentmentCurrencyForModel()
        splashmodel!!.filteredproducts!!.observe(this, Observer<MutableList<Storefront.ProductEdge>> { this.setProductData(it) })
        initializeFirebase();
        val mServiceComponent = ComponentName(this, com.baytrendy.shopifyapp.jobservicessection.JobScheduler::class.java)
        val builder = JobInfo.Builder(101, mServiceComponent)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        builder.setRequiresDeviceIdle(false)
        builder.setMinimumLatency(0)
        builder.setRequiresCharging(false)
        builder.setPersisted(true)
        val extras = PersistableBundle()
        extras.putLong("MageNativeTEST", 10000)
        builder.setExtras(extras)
        tm = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        tm?.schedule(builder.build())
        if (intent != null) {
            val appLinkIntent = intent
            val appLinkAction = appLinkIntent.action
            if (appLinkIntent.data != null) {
                if (appLinkIntent.data!!.getQueryParameters("pid") != null) {
                    try {
                        product_id = appLinkIntent.data!!.getQueryParameters("pid")[0]
                    } catch (e: Exception) {
                        e.printStackTrace()
                        var link_array = appLinkIntent.data!!.toString().replace("https://", "").split("/")
                        productTitle = link_array.get(link_array.size - 1)
                        splashmodel?.getProductsByKeywords(productTitle!!)
                    }
                }
            }
        }
    }

    private fun setProductData(product: MutableList<Storefront.ProductEdge>?) {
        productData = product?.get(0)?.node
    }

    private fun showToast(it: String?) {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

    private fun initializeFirebase() {
        auth = Firebase.auth(firebaseapp!!)
        auth.signInWithEmailAndPassword("sudhanshshah@magenative.com", "asdcxzasd")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val user = auth.currentUser

                        dataBaseReference = getmFirebaseSecondanyInstance().getReference(Urls(context).shopdomain.replace(".myshopify.com", ""))
                        if (splashmodel!!.isLogin) {
                            splashmodel!!.refreshTokenIfRequired()
                        }
                        splashmodel!!.firebaseResponse().observe(this, Observer<FireBaseResponse> { this.consumeResponse(it) })
                        splashmodel!!.Response(resources.getString(R.string.shopdomain)).observe(this, Observer<LocalDbResponse> { this.consumeResponse(it) })
                        splashmodel!!.errorMessageResponse.observe(this, Observer<String> { this.consumeErrorResponse(it) })
                        splashmodel!!.getNotificationCompaign().observe(this, Observer { this.cartNotification(it) })
                        @SuppressLint("HardwareIds") val deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
                        splashmodel!!.sendTokenToServer(deviceId)

                        /* Toast.makeText(baseContext, "Authentication success",
                                 Toast.LENGTH_SHORT).show()*/
                    } else {

                        /*    Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()*/
                    }

                }
    }

    private fun cartNotification(it: Boolean?) {
        if (featuresModel.abandoned_cart_compaigns) {
            tm?.cancel(101)
        }
    }

    private fun consumeErrorResponse(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun consumeResponse(reponse: LocalDbResponse) {
        when (reponse.status) {
            Status.SUCCESS -> renderSuccessResponse(reponse.data!!)
            Status.ERROR -> {
                reponse.error!!.printStackTrace()
                consumeErrorResponse(resources.getString(R.string.errorString))
            }
        }
    }

    private fun consumeResponse(reponse: FireBaseResponse) {
        when (reponse.status) {
            Status.SUCCESS -> renderSuccessResponse(reponse.data!!)
            Status.ERROR -> {
                reponse.error!!.printStackTrace()
                consumeErrorResponse(resources.getString(R.string.errorString))
            }
        }
    }

    private fun renderSuccessResponse(data: AppLocalData) {
        val intent: Array<Intent?>
        if (!data.isIstrialexpire) {
            intent = arrayOfNulls(1)
            intent[0] = Intent(this@Splash, TrialExpired::class.java)
        } else {
            if (product_id != null) {
                intent = arrayOfNulls(2)
                val product = Intent(this@Splash, ProductView::class.java)
                product.putExtra("ID", product_id)
                intent[1] = product
            } else if (productData != null) {
                intent = arrayOfNulls(2)
                val product = Intent(this@Splash, ProductView::class.java)
                product.putExtra("product", productData)
                product.putExtra("tittle", productTitle)
                intent[1] = product
            } else {
                intent = arrayOfNulls(1)
            }
            val homepage = Intent(this@Splash, HomePage::class.java)
            intent[0] = homepage
        }
        Handler().postDelayed(Runnable {
            startActivities(intent)
            Constant.activityTransition(this)
            finish()
        }, 1000)
    }

    private fun renderSuccessResponse(data: DataSnapshot) {
        try {
            val value = data.getValue(String::class.java)
            Log.i("MageNative", "launch_screen : " + value)
            val model = CommanModel()
            model.imageurl = value
            binding!!.commonmodel = model
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

