package com.baytrendy.shopifyapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.baytrendy.shopifyapp.dependecyinjection.MageNativeAppComponent
import com.baytrendy.shopifyapp.dependecyinjection.DaggerMageNativeAppComponent
import com.baytrendy.shopifyapp.dependecyinjection.UtilsModule
import com.baytrendy.shopifyapp.sharedprefsection.MagePrefs
import com.baytrendy.shopifyapp.utils.Urls
import net.danlew.android.joda.JodaTimeAndroid

class MyApplication : MultiDexApplication() {
    var mageNativeAppComponent: MageNativeAppComponent? = null

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        MagePrefs.getInstance(this)
        context = this
        mageNativeAppComponent = DaggerMageNativeAppComponent.builder().utilsModule(UtilsModule(this)).build()
        val options = FirebaseOptions.Builder()
                .setProjectId("shopify-dev-project-2f51e")
                .setApplicationId("1:445702503308:android:f8dee0b320adfdaa68b4a9") // Required for Analytics.
                .setApiKey("AIzaSyD0GhHgrwqVQC7m3LBOkoxVzVefP6EQAZw") // Required for Auth.
                .setDatabaseUrl("https://shopify-dev-project-2f51e.firebaseio.com/") // Required for RTDB.
                .build()
        firebaseapp = FirebaseApp.initializeApp(this /* Context */, options, "MageNative")

    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
    }

    companion object {
        lateinit var context: MyApplication
        lateinit var firebaseapp: FirebaseApp
        private var mFirebaseSecondanyInstance: FirebaseDatabase? = null
        var flag: Boolean = false;
        fun getmFirebaseSecondanyInstance(): FirebaseDatabase {
            if (mFirebaseSecondanyInstance == null) {
                val secondary = FirebaseApp.getInstance("MageNative")
                mFirebaseSecondanyInstance = FirebaseDatabase.getInstance(secondary)
            }
            return mFirebaseSecondanyInstance as FirebaseDatabase
        }

        lateinit var dataBaseReference: DatabaseReference
    }
}
