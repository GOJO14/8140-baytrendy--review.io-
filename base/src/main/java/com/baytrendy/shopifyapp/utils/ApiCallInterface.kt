package com.baytrendy.shopifyapp.utils

import com.google.gson.JsonElement
import com.baytrendy.shopifyapp.utils.Urls.Data.SIZECHART
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.http.*


interface ApiCallInterface {

    @GET(Urls.MENU)
    fun getMenus(@Query("mid") mid: String): Single<JsonElement>

    @GET(Urls.HOMEPAGE)
    fun getHomePage(@Query("mid") mid: String): Single<JsonElement>

    @GET(Urls.SETORDER)
    fun setOrder(@Query("mid") mid: String, @Query("checkout_token") checkout_token: String?): Single<JsonElement>

    @GET(Urls.SETDEVICES)
    fun setDevices(@Query("mid") mid: String, @Query("device_id") device_id: String, @Query("email") email: String, @Query("type") type: String, @Query("unique_id") unique_id: String): Single<JsonElement>

    @Headers(Urls.HEADER) // Add the Domain-Name header
    @POST(Urls.RECOMMENDATION)
    fun getRecommendations(@Header("X-SHOP") shop: String, @Header("X-CLIENT") client: String, @Header("X-ACCESS-TOKEN") token: String, @Header("Content-Type") content_tyepe: String, @Body body: com.baytrendy.shopifyapp.dependecyinjection.Body): Single<JsonElement>

    @GET("installedstatus")
    fun checkInstallStatusReviewApi(@Query("mid") mid: String?, @Query("product_id") productId: String?): Single<JsonElement>

    @GET("index.php/shopifymobile/productreviewapi/badges")
    fun getBadges(@Query("mid") mid: String?, @Query("product_id") productId: String?): Single<JsonElement>

    @GET("index.php/shopifymobile/productreviewapi/product")
    fun getReviewsList(@Query("mid") mid: String?, @Query("product_id") productId: String?, @Query("page") page: Int): Single<JsonElement>

    @GET("index.php/shopifymobile/productreviewapi/create")
    fun createReview(@Query("mid") mid: String?, @Query("review[rating]") reviewRating: String?, @Query("product_id") productId: String?,
                     @Query("review[author]") reviewAuthor: String?, @Query("review[email]") reviewEmail: String?, @Query("review[title]") reviewTitle: String?,
                     @Query("review[body]") reviewBody: String?): Single<JsonElement>


    @GET(SIZECHART)
    fun getSizeChart(@Query("shop") shop: String, @Query("source") source: String, @Query("product") productId: String?,
                     @Query("tags") tags: String, @Query("vendor") vendor: String): String
}
