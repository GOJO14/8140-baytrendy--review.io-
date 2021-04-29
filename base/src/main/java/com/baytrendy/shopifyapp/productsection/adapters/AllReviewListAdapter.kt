package com.baytrendy.shopifyapp.productsection.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity
import com.baytrendy.shopifyapp.databinding.AllReviewListItemBinding
import com.baytrendy.shopifyapp.productsection.models.Review
import javax.inject.Inject

class AllReviewListAdapter @Inject constructor() : RecyclerView.Adapter<AllReviewListAdapter.AllReviewListViewHolder>() {

    var reviwList: ArrayList<Review>? = null


    fun setData(reviwList: ArrayList<Review>?) {
        this.reviwList = reviwList
    }

    class AllReviewListViewHolder : RecyclerView.ViewHolder {
        var binding: AllReviewListItemBinding? = null

        constructor(itemBinding: AllReviewListItemBinding) : super(itemBinding.root) {
            this.binding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllReviewListViewHolder {
        var binding = DataBindingUtil.inflate<AllReviewListItemBinding>(LayoutInflater.from(parent.context), R.layout.all_review_list_item, parent, false)
        var gradientDrawable = GradientDrawable()
        gradientDrawable.setSize(binding.circularName.layoutParams.width, binding.circularName.layoutParams.height)
        gradientDrawable.cornerRadius = (binding.circularName.layoutParams.width / 2).toFloat()
        gradientDrawable.setStroke(2, Color.parseColor(NewBaseActivity.themeColor))
        binding.circularName.background = gradientDrawable
        return AllReviewListViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return reviwList?.size!!
    }

    override fun onBindViewHolder(holder: AllReviewListViewHolder, position: Int) {
        holder.binding?.reviewList = reviwList?.get(position)
        holder.binding?.ratingBar?.rating = reviwList?.get(position)?.rating?.toFloat()!!
        if (reviwList?.get(position)?.reviewerName?.contains(" ")!!) {
            holder.binding?.shortname?.text = reviwList?.get(position)?.reviewerName?.split(" ")?.get(0)?.substring(0, 1) + "" + reviwList?.get(position)?.reviewerName?.split(" ")?.get(1)?.substring(0, 1)
        } else {
            holder.binding?.shortname?.text = reviwList?.get(position)?.reviewerName?.substring(0, 1)
        }
    }
}