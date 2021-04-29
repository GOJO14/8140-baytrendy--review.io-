package com.baytrendy.shopifyapp.wishlistsection.viewholders

import androidx.recyclerview.widget.RecyclerView

import com.baytrendy.shopifyapp.databinding.MWishitemBinding
class WishItem:RecyclerView.ViewHolder{
    var binding:MWishitemBinding
    constructor( binding: MWishitemBinding):super(binding.root){
        this.binding=binding;
    }
}
