package com.baytrendy.shopifyapp.collectionsection.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.MyApplication
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity
import com.baytrendy.shopifyapp.collectionsection.adapters.CollectionRecylerAdapter
import com.baytrendy.shopifyapp.collectionsection.viewmodels.CollectionViewModel
import com.baytrendy.shopifyapp.databinding.MCollectionlistBinding
import com.baytrendy.shopifyapp.searchsection.activities.AutoSearch
import com.baytrendy.shopifyapp.utils.Constant
import com.baytrendy.shopifyapp.utils.ViewModelFactory

import javax.inject.Inject

class CollectionList : NewBaseActivity() {
    private var binding: MCollectionlistBinding? = null

    @Inject
    lateinit var factory: ViewModelFactory
    private var model: CollectionViewModel? = null

    @Inject
    lateinit var adapter: CollectionRecylerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val group = findViewById<ViewGroup>(R.id.container)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.m_collectionlist, group, true)
        showBackButton()
        showTittle(resources.getString(R.string.collection))
        setLayout(binding!!.categorylist, "vertical")
        (application as MyApplication).mageNativeAppComponent!!.doCollectionInjection(this)
        model = ViewModelProviders.of(this, factory).get(CollectionViewModel::class.java)
        model!!.context = this
        model!!.Response().observe(this, Observer<List<Storefront.CollectionEdge>> { this.setRecylerData(it) })
        model!!.message.observe(this, Observer<String> { this.showToast(it) })
        binding?.clickHandler = this
    }

    fun clickSearch(view: View) {
        val searchpage = Intent(this, AutoSearch::class.java)
        startActivity(searchpage)
        Constant.activityTransition(this)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun setRecylerData(collections: List<Storefront.CollectionEdge>) {
        try {
            if (collections.size > 0) {
                Log.i("MageNative", "images" + collections.size)
                Log.i("MageNative", "collection id" + collections.get(0).node.id)
                adapter!!.setData(collections, this)
                binding!!.categorylist.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else {
                showToast(resources.getString(R.string.nocollection))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
