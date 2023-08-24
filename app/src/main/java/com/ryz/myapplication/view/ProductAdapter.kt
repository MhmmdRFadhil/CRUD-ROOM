package com.ryz.myapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ryz.myapplication.databinding.RowItemProductListBinding
import com.ryz.myapplication.model.local.entity.ProductData

class ProductAdapter : ListAdapter<ProductData, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {
    inner class ProductViewHolder(private val binding: RowItemProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productData: ProductData) {
            with(binding) {
                tvProductName.text = productData.productName

                if (productData.isSell == true) {
                    tvSellingPrice.text = productData.sellingPrice.toString()
                } else {
                    tvSellingPriceTitle.isVisible = false
                    tvSellingPrice.isVisible = false
                }

                if (productData.isBuy == true) {
                    tvPurchasePrice.text = productData.purchasePrice.toString()
                } else {
                    tvPurchasePriceTitle.isVisible = false
                    tvPurchasePrice.isVisible = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            RowItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductData>() {
            override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean =
                oldItem == newItem
        }
    }
}