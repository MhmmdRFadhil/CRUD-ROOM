package com.ryz.myapplication.view

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ryz.myapplication.MainActivity
import com.ryz.myapplication.R
import com.ryz.myapplication.databinding.RowItemProductListBinding
import com.ryz.myapplication.model.local.entity.ProductData
import com.ryz.myapplication.viewmodel.ProductViewModel

class ProductAdapter : ListAdapter<ProductData, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {
    inner class ProductViewHolder(private val binding: RowItemProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var productViewModel: ProductViewModel
        fun bind(productData: ProductData) {
            productViewModel = (itemView.context as MainActivity).productViewModel
            with(binding) {
                tvProductName.text = productData.productName
                showHideSellingPrice(productData.isSell, productData.sellingPrice)
                showHidePurchasePrice(productData.isBuy, productData.purchasePrice)
                imgDelete.setOnClickListener { showAlertDialog(productData) }
            }
        }

        private fun showHideSellingPrice(isSell: Boolean?, sellingPrice: Long?) {
            with(binding) {
                if (isSell == true) {
                    tvSellingPrice.text = sellingPrice.toString()
                } else {
                    tvSellingPriceTitle.isVisible = false
                    tvSellingPrice.isVisible = false
                }
            }
        }

        private fun showHidePurchasePrice(isBuy: Boolean?, purchasePrice: Long?) {
            with(binding) {
                if (isBuy == true) {
                    tvPurchasePrice.text = purchasePrice.toString()
                } else {
                    tvPurchasePriceTitle.isVisible = false
                    tvPurchasePrice.isVisible = false
                }
            }
        }

        private fun showAlertDialog(productData: ProductData) {
            AlertDialog.Builder(itemView.context).apply {
                setTitle(context.getString(R.string.delete_alert_title))
                setMessage(context.getString(R.string.delete_alert_message))
                setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                    productViewModel.deleteProduct(productData)
                }
                setNegativeButton(context.getString(R.string.no), null)
            }.create().show()
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