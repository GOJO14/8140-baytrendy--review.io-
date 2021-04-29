package com.baytrendy.shopifyapp.addresssection.viewholders
import androidx.recyclerview.widget.RecyclerView
import com.baytrendy.shopifyapp.databinding.MAddressitemBinding
class AddressViewHolder :RecyclerView.ViewHolder{
    var binding: MAddressitemBinding
    constructor(binding: MAddressitemBinding) : super(binding.root) {
        this.binding=binding;
    }
}

