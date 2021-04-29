package com.baytrendy.shopifyapp.cartsection.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class CartListItem : BaseObservable() {
    var product_id: String? = null
    var variant_id: String? = null
    var productname: String? = null
    var normalprice: String? = null
    var specialprice: String? = null
    var variant_one: String? = null
    var variant_two: String? = null
    var variant_three: String? = null
    var image: String? = null
    var offertext: String? = null

    @get:Bindable
    var qty: String? = null
        set(qty) {
            field = qty
            notifyPropertyChanged(BR.qty)
        }

    @get:Bindable
    var position: Int = 0
        set(position) {
            field = position
            notifyPropertyChanged(BR.position)
        }
}
