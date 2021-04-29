package com.baytrendy.shopifyapp.productsection.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.baytrendy.shopifyapp.MyApplication
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity
import com.baytrendy.shopifyapp.cartsection.activities.CartList
import com.baytrendy.shopifyapp.databinding.ActivityAllReviewListBinding
import com.baytrendy.shopifyapp.productsection.adapters.AllReviewListAdapter
import com.baytrendy.shopifyapp.productsection.adapters.ReviewListAdapter
import com.baytrendy.shopifyapp.productsection.models.Review
import com.baytrendy.shopifyapp.productsection.models.ReviewModel
import com.baytrendy.shopifyapp.productsection.viewmodels.ProductViewModel
import com.baytrendy.shopifyapp.utils.ApiResponse
import com.baytrendy.shopifyapp.utils.Constant
import com.baytrendy.shopifyapp.utils.Urls
import com.baytrendy.shopifyapp.utils.ViewModelFactory
import org.json.JSONObject
import javax.inject.Inject

class AllReviewListActivity : NewBaseActivity() {

    @Inject
    lateinit var reviewAdapter: AllReviewListAdapter

    private var reviewBinding: ActivityAllReviewListBinding? = null
    private var reviewList: ArrayList<Review>? = ArrayList<Review>()
    private var product_id: String? = null
    private var page: Int = 1
    private var isLoading: Boolean = true

    @Inject
    lateinit var factory: ViewModelFactory
    private var model: ProductViewModel? = null


    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = recyclerView.layoutManager!!.childCount
            val totalItemCount = recyclerView.layoutManager!!.itemCount
            var firstVisibleItemPosition = 0
            if (recyclerView.layoutManager is LinearLayoutManager) {
                firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            } else if (recyclerView.layoutManager is GridLayoutManager) {
                firstVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            }
            if (!recyclerView.canScrollVertically(1)) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0
                        && totalItemCount >= reviewList!!.size && isLoading) {
                    page++
                    model?.getReviews(Urls(application as MyApplication).mid, product_id!!, page)?.observe(this@AllReviewListActivity, Observer { this@AllReviewListActivity.consumeReview(it) })
                }
            }
        }
    }

    private fun consumeReview(response: ApiResponse?) {
        try {
            if (JSONObject(response?.data.toString()).getJSONObject("data").has("reviews")) {
                if (JSONObject(response?.data.toString()).getJSONObject("data").getJSONArray("reviews").length() > 0) {
                    var reviewModel = Gson().fromJson<ReviewModel>(response?.data.toString(), ReviewModel::class.java) as ReviewModel
                    if (reviewModel?.success!!) {
                        if (reviewModel?.data?.reviews?.size!! > 0) {
                            reviewAdapter?.reviwList?.addAll(reviewModel?.data?.reviews!!)
                            reviewAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            isLoading = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val group = findViewById<ViewGroup>(R.id.container)
        reviewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_all_review_list, group, true)
        (application as MyApplication).mageNativeAppComponent!!.doReviewListInjection(this)
        model = ViewModelProvider(this, factory).get(ProductViewModel::class.java)
        model!!.context = this
        showBackButton()
        if (intent.hasExtra("reviewList")) {
            var reviewModel = intent.getSerializableExtra("reviewList") as ReviewModel
            reviewList = reviewModel.data?.reviews as ArrayList<Review>?
            product_id = intent.getStringExtra("product_id")
            reviewAdapter.setData(reviewList)
            reviewBinding?.reviewList?.adapter = reviewAdapter
            showTittle(intent.getStringExtra("product_name"))
        }
        reviewBinding?.reviewList?.addOnScrollListener(recyclerViewOnScrollListener)
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