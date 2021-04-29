package com.baytrendy.shopifyapp.basesection.models

import android.text.Spanned
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.shopify.buy3.Storefront

class ListData : BaseObservable() {

    var textdata: String? = null
    var product: Storefront.Product? = null

    var description: String? = null
    var descriptionhmtl: Spanned? = null

    @Bindable
    var specialprice: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.specialprice)
        }

    @Bindable
    var regularprice: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.regularprice)
        }

    @Bindable
    var offertext: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.offertext)
        }

    @get:Bindable
    var addtowish: String? = null
        set(addtowish) {
            field = addtowish
            notifyPropertyChanged(BR.addtowish)
        }

    var isStrike: Boolean = false
    var arimage: String? = null

}
