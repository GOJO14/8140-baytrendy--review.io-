package com.baytrendy.shopifyapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.baytrendy.shopifyapp.addresssection.viewmodels.AddressModel
import com.baytrendy.shopifyapp.basesection.viewmodels.LeftMenuViewModel
import com.baytrendy.shopifyapp.basesection.viewmodels.SplashViewModel
import com.baytrendy.shopifyapp.cartsection.viewmodels.CartListViewModel
import com.baytrendy.shopifyapp.checkoutsection.viewmodels.CheckoutWebLinkViewModel
import com.baytrendy.shopifyapp.collectionsection.viewmodels.CollectionMenuViewModel
import com.baytrendy.shopifyapp.collectionsection.viewmodels.CollectionViewModel
import com.baytrendy.shopifyapp.homesection.viewmodels.HomePageViewModel
import com.baytrendy.shopifyapp.loginsection.viewmodels.LoginViewModel
import com.baytrendy.shopifyapp.loginsection.viewmodels.RegistrationViewModel
import com.baytrendy.shopifyapp.ordersection.viewmodels.OrderListViewModel
import com.baytrendy.shopifyapp.personalised.viewmodels.PersonalisedViewModel
import com.baytrendy.shopifyapp.productsection.viewmodels.ProductListModel
import com.baytrendy.shopifyapp.productsection.viewmodels.ProductViewModel
import com.baytrendy.shopifyapp.repositories.Repository
import com.baytrendy.shopifyapp.searchsection.viewmodels.SearchListModel
import com.baytrendy.shopifyapp.userprofilesection.viewmodels.UserProfileViewModel
import com.baytrendy.shopifyapp.wishlistsection.viewmodels.WishListViewModel

import javax.inject.Inject

class ViewModelFactory @Inject
constructor(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(LeftMenuViewModel::class.java)) {
            return LeftMenuViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(ProductListModel::class.java)) {
            return ProductListModel(repository) as T
        }
        if (modelClass.isAssignableFrom(CollectionViewModel::class.java)) {
            return CollectionViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(CollectionMenuViewModel::class.java)) {
            return CollectionMenuViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(WishListViewModel::class.java)) {
            return WishListViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(CartListViewModel::class.java)) {
            return CartListViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(CheckoutWebLinkViewModel::class.java)) {
            return CheckoutWebLinkViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(SearchListModel::class.java)) {
            return SearchListModel(repository) as T
        }
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(OrderListViewModel::class.java)) {
            return OrderListViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(AddressModel::class.java)) {
            return AddressModel(repository) as T
        }
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            return HomePageViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(PersonalisedViewModel::class.java)) {
            return PersonalisedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
