package com.baytrendy.shopifyapp.productsection.viewmodels

import android.content.Context
import android.os.Handler
import android.os.Looper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.QueryGraphCall
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.Error
import com.baytrendy.shopifyapp.basesection.viewmodels.SplashViewModel.Companion.featuresModel
import com.baytrendy.shopifyapp.network_transaction.CustomResponse
import com.baytrendy.shopifyapp.network_transaction.doGraphQLQueryGraph
import com.baytrendy.shopifyapp.repositories.Repository
import com.baytrendy.shopifyapp.shopifyqueries.Query
import com.baytrendy.shopifyapp.utils.GraphQLResponse
import com.baytrendy.shopifyapp.utils.Status

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class ProductListModel(var repository: Repository) : ViewModel() {
    private var categoryID = ""
    var shopID = ""
    val presentmentCurrency: String
        get() {
            val currency = arrayOf("nopresentmentcurrency")
            try {
                val executor = Executors.newSingleThreadExecutor()
                val callable = Callable {
                    if (repository.localData[0].currencycode != null) {
                        currency[0] = repository.localData[0].currencycode!!
                    }
                    currency[0]
                }
                val future = executor.submit(callable)
                currency[0] = future.get()
                executor.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return currency[0]
        }
    var collectionData: MutableLiveData<Storefront.Collection> = MutableLiveData<Storefront.Collection>()
    private var categoryHandle = ""
    var cursor = "nocursor"
        set(cursor) {
            field = cursor
            Response()
        }
    var isDirection = false
    var sortKeys: Storefront.ProductCollectionSortKeys = Storefront.ProductCollectionSortKeys.BEST_SELLING
    var keys: Storefront.ProductSortKeys = Storefront.ProductSortKeys.BEST_SELLING
    var number = 10
    private val disposables = CompositeDisposable()
    val message = MutableLiveData<String>()
    val filteredproducts = MutableLiveData<MutableList<Storefront.ProductEdge>>()
    lateinit var context: Context
    fun getcategoryID(): String {
        return categoryID
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

    fun setcategoryID(categoryID: String) {
        this.categoryID = categoryID
    }

    fun getcategoryHandle(): String {
        return categoryHandle
    }

    fun setcategoryHandle(categoryHandle: String) {
        this.categoryHandle = categoryHandle
    }

    fun Response() {
        if (!getcategoryID().isEmpty()) {
            getProductsById()
        }
        if (!getcategoryHandle().isEmpty()) {
            getProductsByHandle()
        }
        if (!shopID.isEmpty()) {
            getAllProducts()
        }
    }

    private fun getAllProducts() {
        var currency_list = ArrayList<Storefront.CurrencyCode>()
        if (presentmentCurrency != "nopresentmentcurrency") {
            currency_list.add(Storefront.CurrencyCode.valueOf(presentmentCurrency!!))
        }
        try {
            doGraphQLQueryGraph(repository, Query.getAllProducts(cursor, keys, isDirection, number, currency_list), customResponse = object : CustomResponse {
                override fun onSuccessQuery(result: GraphCallResult<Storefront.QueryRoot>) {
                    invoke(result)
                }
            }, context = context)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getProductsById() {
        var currency_list = ArrayList<Storefront.CurrencyCode>()
        if (presentmentCurrency != "nopresentmentcurrency") {
            currency_list.add(Storefront.CurrencyCode.valueOf(presentmentCurrency!!))
        }

        try {
            doGraphQLQueryGraph(repository, Query.getProductsById(getcategoryID(), cursor, sortKeys, isDirection, number, currency_list), customResponse = object : CustomResponse {
                override fun onSuccessQuery(result: GraphCallResult<Storefront.QueryRoot>) {
                    invoke(result)
                }
            }, context = context)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getProductsByHandle() {
        var currency_list = ArrayList<Storefront.CurrencyCode>()
        if (presentmentCurrency != "nopresentmentcurrency") {
            currency_list.add(Storefront.CurrencyCode.valueOf(presentmentCurrency!!))
        }
        try {
            doGraphQLQueryGraph(repository, Query.getProductsByHandle(getcategoryHandle(), cursor, sortKeys, isDirection, number, currency_list), customResponse = object : CustomResponse {
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
            consumeResponse(GraphQLResponse.success(result as GraphCallResult.Success<*>))
        } else {
            consumeResponse(GraphQLResponse.error(result as GraphCallResult.Failure))
        }
        return Unit
    }

    private fun consumeResponse(reponse: GraphQLResponse) {
        when (reponse.status) {
            Status.SUCCESS -> {
                val result = (reponse.data as GraphCallResult.Success<Storefront.QueryRoot>).response
                if (result.hasErrors) {
                    val errors = result.errors
                    val iterator = errors.iterator()
                    val errormessage = StringBuilder()
                    var error: Error? = null
                    while (iterator.hasNext()) {
                        error = iterator.next()
                        errormessage.append(error.message())
                    }
                    message.setValue(errormessage.toString())
                } else {
                    var edges: List<Storefront.ProductEdge>? = null
                    if (!getcategoryHandle().isEmpty()) {
                        edges = result.data!!.collectionByHandle.products.edges
                    }
                    if (!getcategoryID().isEmpty()) {
                        if (result.data!!.node != null) {
                            edges = (result.data?.node as Storefront.Collection).products.edges
                            collectionData.value = result.data?.node as Storefront.Collection
                        }
                    }
                    if (!shopID.isEmpty()) {
                        edges = result.data!!.products.edges
                    }
                    filterProduct(edges)
                }
            }
            Status.ERROR -> message.setValue(reponse.error!!.error.message)
            else -> {
            }
        }
    }

    fun filterProduct(list: List<Storefront.ProductEdge>?) {
        try {
            if (featuresModel.outOfStock!!) {
                disposables.add(repository.getProductList(list!!)
                        .subscribeOn(Schedulers.io())
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result -> filteredproducts.setValue(result) })
            } else {
                disposables.add(repository.getProductList(list!!)
                        .subscribeOn(Schedulers.io())
                        .filter { x -> x.node.availableForSale }
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result -> filteredproducts.setValue(result) })
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onCleared() {
        disposables.clear()
    }
}
