package com.baytrendy.shopifyapp.dependecyinjection

import com.baytrendy.shopifyapp.addresssection.activities.AddressList
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity
import com.baytrendy.shopifyapp.basesection.activities.Splash
import com.baytrendy.shopifyapp.basesection.fragments.LeftMenu
import com.baytrendy.shopifyapp.cartsection.activities.CartList
import com.baytrendy.shopifyapp.checkoutsection.activities.CheckoutWeblink
import com.baytrendy.shopifyapp.checkoutsection.activities.OrderSuccessActivity
import com.baytrendy.shopifyapp.collectionsection.activities.CollectionList
import com.baytrendy.shopifyapp.collectionsection.activities.CollectionListMenu
import com.baytrendy.shopifyapp.homesection.activities.HomePage
import com.baytrendy.shopifyapp.homesection.viewmodels.HomePageViewModel
import com.baytrendy.shopifyapp.jobservicessection.JobScheduler
import com.baytrendy.shopifyapp.loginsection.activity.LoginActivity
import com.baytrendy.shopifyapp.loginsection.activity.RegistrationActivity
import com.baytrendy.shopifyapp.ordersection.activities.OrderList
import com.baytrendy.shopifyapp.productsection.activities.AllReviewListActivity
import com.baytrendy.shopifyapp.productsection.activities.ProductList
import com.baytrendy.shopifyapp.productsection.activities.ProductView
import com.baytrendy.shopifyapp.productsection.activities.ZoomActivity
import com.baytrendy.shopifyapp.quickadd_section.activities.QuickAddActivity
import com.baytrendy.shopifyapp.searchsection.activities.AutoSearch
import com.baytrendy.shopifyapp.userprofilesection.activities.UserProfile
import com.baytrendy.shopifyapp.utils.Urls
import com.baytrendy.shopifyapp.wishlistsection.activities.WishList

import javax.inject.Singleton

import dagger.Component

@Component(modules = [UtilsModule::class])
@Singleton
interface MageNativeAppComponent {

    fun doSplashInjection(splash: Splash)
    fun doProductListInjection(product: ProductList)
    fun doCollectionInjection(collectionList: CollectionList)
    fun doCollectionInjection(collectionList: CollectionListMenu)
    fun doProductViewInjection(product: ProductView)
    fun doReviewListInjection(reviewListActivity: AllReviewListActivity)
    fun doZoomActivityInjection(base: ZoomActivity)
    fun doBaseActivityInjection(base: NewBaseActivity)
    fun doWishListActivityInjection(wish: WishList)
    fun doCartListActivityInjection(cart: CartList)
    fun doCheckoutWeblinkActivityInjection(cart: CheckoutWeblink)
    fun doAutoSearchActivityInjection(cart: AutoSearch)
    fun doLoginActivtyInjection(loginActivity: LoginActivity)
    fun doRegistrationActivityInjection(registrationActivity: RegistrationActivity)
    fun doLeftMeuInjection(left: LeftMenu)
    fun doUserProfileInjection(profile: UserProfile)
    fun doOrderListInjection(profile: OrderList)
    fun doAddressListInjection(addressList: AddressList)
    fun doHomePageInjection(home: HomePage)
    fun doHomePageModelInjection(home: HomePageViewModel)
    fun orderSuccessInjection(orderSuccessActivity: OrderSuccessActivity)
    fun quickAddInjection(quickAddActivity: QuickAddActivity)
    fun doServiceInjection(job: JobScheduler)
    fun doURlInjection(urls: Urls)
}
