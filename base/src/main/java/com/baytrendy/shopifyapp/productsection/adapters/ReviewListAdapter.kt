package com.baytrendy.shopifyapp.productsection.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.baytrendy.shopifyapp.R
import com.baytrendy.shopifyapp.basesection.activities.NewBaseActivity.Companion.themeColor
import com.baytrendy.shopifyapp.databinding.ReviewListItemBinding
import com.baytrendy.shopifyapp.productsection.models.Review
import javax.inject.Inject

class ReviewListAdapter @Inject
constructor() : RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder>() {

    var reviwList: List<Review>? = null


    fun setData(reviwList: List<Review>?) {
        this.reviwList = reviwList
    }

    class ReviewListViewHolder : RecyclerView.ViewHolder {
        var binding: ReviewListItemBinding? = null

        constructor(itemBinding: ReviewListItemBinding) : super(itemBinding.root) {
            this.binding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder {
        var binding = DataBindingUtil.inflate<ReviewListItemBinding>(LayoutInflater.from(parent.context), R.layout.review_list_item, parent, false)
        binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        var gradientDrawable = GradientDrawable()
        gradientDrawable.setSize(binding.circularName.layoutParams.width, binding.circularName.layoutParams.height)
        gradientDrawable.cornerRadius = (binding.circularName.layoutParams.width / 2).toFloat()
        gradientDrawable.setStroke(2, Color.parseColor(themeColor))
        binding.circularName.background = gradientDrawable
        return ReviewListViewHolder(binding)
    }


    override fun getItemCount(): Int {
        if (reviwList?.size!! <= 5)
            return reviwList?.size!!
        else
            return 5
    }

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        holder.binding?.reviewList = reviwList?.get(position)
        holder.binding?.ratingBar?.rating = reviwList?.get(position)?.rating?.toFloat()!!
        if (reviwList?.get(position)?.reviewerName?.contains(" ")!!) {
            holder.binding?.shortname?.text = reviwList?.get(position)?.reviewerName?.split(" ")?.get(0)?.substring(0, 1) + "" + reviwList?.get(position)?.reviewerName?.split(" ")?.get(1)?.substring(0, 1)
        } else {
            holder.binding?.shortname?.text = reviwList?.get(position)?.reviewerName?.substring(0, 1)
        }
    }
}