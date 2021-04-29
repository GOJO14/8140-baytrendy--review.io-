package com.baytrendy.shopifyapp.productsection.viewmodels

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.Toast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement

import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.QueryGraphCall
import com.shopify.buy3.Storefront
import com.baytrendy.shopifyapp.basesection.viewmodels.SplashViewModel
import com.baytrendy.shopifyapp.dbconnection.entities.CartItemData
import com.baytrendy.shopifyapp.dbconnection.entities.ItemData
import com.baytrendy.shopifyapp.dependecyinjection.Body
import com.baytrendy.shopifyapp.dependecyinjection.InnerData
import com.baytrendy.shopifyapp.network_transaction.CustomResponse
import com.baytrendy.shopifyapp.network_transaction.doGraphQLQueryGraph
import com.baytrendy.shopifyapp.network_transaction.doRetrofitCall
import com.baytrendy.shopifyapp.productsection.models.VariantData
import com.baytrendy.shopifyapp.repositories.Repository
import com.baytrendy.shopifyapp.shopifyqueries.Query
import com.baytrendy.shopifyapp.utils.ApiResponse
import com.baytrendy.shopifyapp.utils.GraphQLResponse
import com.baytrendy.shopifyapp.utils.Urls
import com.baytrendy.shopifyapp.utils.Urls.Data.SIZECHART
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import java.io.UnsupportedEncodingException
import java.lang.Runnable
import java.net.URL
import java.net.URLEncoder
import java.util.regex.Pattern

class ProductViewModel(private val repository: Repository) : ViewModel() {
    var handle = ""
    var id = ""
    var presentmentCurrency: String? = null
    private val disposables = CompositeDisposable()
    private val responseLiveData = MutableLiveData<GraphQLResponse>()
    val recommendedLiveData = MutableLiveData<GraphQLResponse>()
    var reviewResponse: MutableLiveData<ApiResponse>? = null
    var reviewBadges: MutableLiveData<ApiResponse>? = MutableLiveData<ApiResponse>()
    var createreviewResponse = MutableLiveData<ApiResponse>()
    var sizeChartVisibility = MutableLiveData<Boolean>()
    var sizeChartUrl = MutableLiveData<String>()
    lateinit var context: Context
    val filteredlist = MutableLiveData<List<Storefront.ProductVariantEdge>>()


    fun getReviews(mid: String, product_id: String, page: Int): MutableLiveData<ApiResponse> {
        reviewResponse = MutableLiveData<ApiResponse>()
        getProductReviews(mid, product_id, page)
        return reviewResponse!!
    }

    fun getReviewBadges(mid: String, product_id: String): MutableLiveData<ApiResponse> {
        getbadgeReviews(mid, product_id)
        return reviewBadges!!
    }

    fun getProductReviews(mid: String, product_id: String, page: Int) {

        doRetrofitCall(repository.getProductReviews(mid, product_id, page), disposables, customResponse = object : CustomResponse {
            override fun onSuccessRetrofit(result: JsonElement) {
                reviewResponse?.setValue(ApiResponse.success(result))
            }

            override fun onErrorRetrofit(error: Throwable) {
                reviewResponse?.setValue(ApiResponse.error(error))
            }
        }, context = context)

    }


    fun getbadgeReviews(mid: String, product_id: String) {

        doRetrofitCall(repository.getbadgeReviews(mid, product_id), disposables, customResponse = object : CustomResponse {
            override fun onSuccessRetrofit(result: JsonElement) {
                reviewBadges?.setValue(ApiResponse.success(result))
            }

            override fun onErrorRetrofit(error: Throwable) {
                reviewBadges?.setValue(ApiResponse.error(error))
            }
        }, context = context)

    }

    fun getcreateReview(mid: String, reviewRating: String, product_id: String, reviewAuthor: String, reviewEmail: String, reviewTitle: String, reviewBody: String) {
        doRetrofitCall(repository.getcreateReview(mid, reviewRating, product_id, reviewAuthor, reviewEmail, reviewTitle, reviewBody), disposables, customResponse = object : CustomResponse {
            override fun onSuccessRetrofit(result: JsonElement) {
                createreviewResponse.setValue(ApiResponse.success(result))
            }

            override fun onErrorRetrofit(error: Throwable) {
                createreviewResponse.setValue(ApiResponse.error(error))
            }
        }, context = context)
    }

    val cartCount: Int
        get() {
            val count = intArrayOf(0)
            try {
                val executor = Executors.newSingleThreadExecutor()
                val callable = Callable {
                    if (repository.allCartItems.size > 0) {
                        count[0] = repository.allCartItems.size
                    }
                    count[0]
                }
                val future = executor.submit(callable)
                count[0] = future.get()
                executor.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return count[0]
        }

    fun shopifyRecommended() {
        var currency_list = ArrayList<Storefront.CurrencyCode>()
        if (presentmentCurrency != "nopresentmentcurrency") {
            currency_list.add(Storefront.CurrencyCode.valueOf(presentmentCurrency!!))
        }
        if (SplashViewModel.featuresModel.recommendedProducts) {
            getRecommendedProducts(currency_list)
        }
    }

    fun Response(): MutableLiveData<GraphQLResponse> {
        var currency_list = ArrayList<Storefront.CurrencyCode>()
        if (presentmentCurrency != "nopresentmentcurrency") {
            currency_list.add(Storefront.CurrencyCode.valueOf(presentmentCurrency!!))
        }
        if (!id.isEmpty()) {
            getProductsById(currency_list)
        }
        if (!handle.isEmpty()) {
            getProductsByHandle(currency_list)
        }
        return responseLiveData
    }

    private fun getRecommendedProducts(currencyList: ArrayList<Storefront.CurrencyCode>) {
        try {
            doGraphQLQueryGraph(repository, Query.recommendedProducts(id, currencyList), customResponse = object : CustomResponse {
                override fun onSuccessQuery(result: GraphCallResult<Storefront.QueryRoot>) {
                    invokeRecommended(result)
                }
            }, context = context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun invokeRecommended(result: GraphCallResult<Storefront.QueryRoot>) {
        if (result is GraphCallResult.Success<*>) {
            recommendedLiveData.setValue(GraphQLResponse.success(result as GraphCallResult.Success<*>))
        } else {
            recommendedLiveData.setValue(GraphQLResponse.error(result as GraphCallResult.Failure))
        }
    }

    private fun getProductsById(currency_list: ArrayList<Storefront.CurrencyCode>) {
        try {
            doGraphQLQueryGraph(repository, Query.getProductById(id, currency_list), customResponse = object : CustomResponse {
                override fun onSuccessQuery(result: GraphCallResult<Storefront.QueryRoot>) {
                    invoke(result)
                }
            }, context = context)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getProductsByHandle(currency_list: ArrayList<Storefront.CurrencyCode>) {
        try {
            doGraphQLQueryGraph(repository, Query.getProductByHandle(handle, currency_list), customResponse = object : CustomResponse {
                override fun onSuccessQuery(result: GraphCallResult<Storefront.QueryRoot>) {
                    invoke(result)
                }
            }, context = context)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private operator fun invoke(result: GraphCallResult<Storefront.QueryRoot>): Unit {
        if (result is GraphCallResult.Success<*>) {
            responseLiveData.setValue(GraphQLResponse.success(result as GraphCallResult.Success<*>))
        } else {
            responseLiveData.setValue(GraphQLResponse.error(result as GraphCallResult.Failure))
        }
        return Unit
    }

    fun setPresentmentCurrencyForModel(): Boolean {
        val isadded = booleanArrayOf(false)
        try {
            val executor = Executors.newSingleThreadExecutor()
            val callable = Callable {
                if (repository.localData[0].currencycode == null) {
                    presentmentCurrency = "nopresentmentcurrency"
                } else {
                    presentmentCurrency = repository.localData[0].currencycode
                }
                isadded[0] = true
                isadded[0]
            }
            val future = executor.submit(callable)
            isadded[0] = future.get()
            executor.shutdown()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isadded[0]
    }

    fun setWishList(product_id: String): Boolean {
        val isadded = booleanArrayOf(false)
        try {
            val executor = Executors.newSingleThreadExecutor()
            val callable = Callable {
                if (repository.getSingleData(product_id) == null) {
                    Log.i("MageNative", "WishListCount : " + repository.wishListData.size)
                    val data = ItemData()
                    data.product_id = product_id
                    repository.insertWishListData(data)
                    Log.i("MageNative", "WishListCount 2: " + repository.wishListData.size)
                    isadded[0] = true
                }
                isadded[0]
            }
            val future = executor.submit(callable)
            isadded[0] = future.get()
            executor.shutdown()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isadded[0]
    }

    fun deleteData(product_id: String) {
        try {
            val runnable = Runnable {
                try {
                    val data = repository.getSingleData(product_id)
                    repository.deleteSingleData(data)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            Thread(runnable).start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun filterList(list: List<Storefront.ProductVariantEdge>) {
        try {
            disposables.add(repository.getList(list)
                    .subscribeOn(Schedulers.io())
                    //  .filter { x -> x.node.availableForSale }
                    .toList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result -> filteredlist.setValue(result) })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onCleared() {
        disposables.clear()
    }

    public fun isInwishList(variantId: String): Boolean {
        val isadded = booleanArrayOf(false)
        try {
            val executor = Executors.newSingleThreadExecutor()
            val callable = Callable {
                if (repository.getSingleData(variantId) != null) {

                    Log.i("MageNative", "item already in wishlist : ")
                    isadded[0] = true
                }
                isadded[0]
            }
            val future = executor.submit(callable)
            isadded[0] = future.get()
            executor.shutdown()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isadded[0]
    }

    fun addToCart(variantId: String, quantity: Int) {
        try {
            val runnable = Runnable {
                val data: CartItemData
                if (repository.getSingLeItem(variantId) == null) {
                    data = CartItemData()
                    data.variant_id = variantId
                    data.qty = quantity
                    repository.addSingLeItem(data)
                } else {
                    data = repository.getSingLeItem(variantId)
                    val qty = data.qty + quantity
                    data.qty = qty
                    repository.updateSingLeItem(data)
                }
                Log.i("MageNative", "CartCount : " + repository.allCartItems.size)
            }
            Thread(runnable).start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getQtyInCart(variantId: String): Int {
        var variant_qty = runBlocking {
            if (repository.getSingLeItem(variantId) == null) {
                return@runBlocking 0
            } else {
                return@runBlocking repository.getSingLeItem(variantId).qty
            }
        }
        return variant_qty
    }

    private val api = MutableLiveData<ApiResponse>()
    fun getApiResponse(): MutableLiveData<ApiResponse> {
        return api
    }

    fun getRecommendations(id: String) {
        RetrofitUrlManager.getInstance().putDomain("douban", Urls.PERSONALISED);
        try {
            var query = InnerData()
            query.id = "query1"
            query.maxRecommendations = 8
            query.recommendationType = "similar_products"
            var list = mutableListOf<Long>()
            var s = String(Base64.decode(id, Base64.DEFAULT))
            list.add(s.replace("gid://shopify/Product/", "").toLong())
            query.productIds = list
            var body = Body()
            body.queries = mutableListOf(query)
            Log.i("Body", "" + list)
            doRetrofitCall(repository.getRecommendation(body), disposables, customResponse = object : CustomResponse {
                override fun onSuccessRetrofit(result: JsonElement) {
                    api.setValue(ApiResponse.success(result))
                }

                override fun onErrorRetrofit(error: Throwable) {
                    api.setValue(ApiResponse.error(error))
                }
            }, context = context)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSizeChart(shop: String, source: String, product_id: String, tags: String, vendor: String) {
        RetrofitUrlManager.getInstance().putDomain("douban", Urls.SIZECHART)
        var hashMap = HashMap<String, String>()
        hashMap.put("shop", shop)
        hashMap.put("source", source)
        hashMap.put("product", product_id)
        hashMap.put("tags", tags)
        hashMap.put("vendor", vendor)
        Log.d("OKHttp", "" + SIZECHART + "?" + getPostDataString(hashMap))
        sizeChartUrl.value = SIZECHART + "?" + getPostDataString(hashMap)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                coroutineScope {
                    var result = async(Dispatchers.IO) {
                        URL(SIZECHART + "?" + getPostDataString(hashMap)).readText()
                    }
                    parseResponse(result.await())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseResponse(await: String) {
        if (await.length == 0) {
            sizeChartVisibility.value = false
        } else {
            sizeChartVisibility.value = true
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getPostDataString(params: HashMap<String, String>): String? {
        val result = StringBuilder()
        var first = true
        for ((key, value) in params.entries) {
            if (first) first = false else result.append("&")
            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value, "UTF-8"))
        }
        Log.i("POST_STRING", "" + result)
        return result.toString()
    }

    fun isValidEmail(target: String): Boolean {
        val emailPattern = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE)
        return emailPattern.matcher(target).matches()
    }
}
