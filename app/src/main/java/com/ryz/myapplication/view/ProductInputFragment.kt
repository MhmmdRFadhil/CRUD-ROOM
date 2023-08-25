package com.ryz.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.ryz.myapplication.MainActivity
import com.ryz.myapplication.R
import com.ryz.myapplication.common.customToolbar
import com.ryz.myapplication.common.hideSoftInput
import com.ryz.myapplication.databinding.FragmentProductInputBinding
import com.ryz.myapplication.model.local.entity.ProductData
import com.ryz.myapplication.viewmodel.ProductViewModel

class ProductInputFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProductInputBinding? = null
    private val binding get() = _binding!!
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = (activity as MainActivity).productViewModel

        setUpToolbar()
        setUpMenu()

        setupSellCheckBox()
        setupBuyCheckBox()

        binding.btnSave.setOnClickListener(this)
    }

    private fun setUpToolbar() {
        binding.layoutToolbars.topAppBar.customToolbar(
            activity as MainActivity,
            title = getString(R.string.product_input_title),
            isShowMenu = true,
            menuId = R.menu.product_list
        )
    }

    private fun setUpMenu() {
        binding.layoutToolbars.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.product_list -> {
                    findNavController().navigate(R.id.action_productInputFragment_to_productListFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun setupSellCheckBox() {
        binding.cbSell.setOnCheckedChangeListener { _, isChecked ->
            showHideSellPrice(isChecked)
        }
    }

    private fun setupBuyCheckBox() {
        binding.cbBuy.setOnCheckedChangeListener { _, isChecked ->
            showHideBuyPrice(isChecked)
        }
    }

    private fun showHideSellPrice(isShow: Boolean) {
        with(binding) {
            tvSellingPrice.isVisible = isShow
            edtSellingPrice.isVisible = isShow
        }
    }

    private fun showHideBuyPrice(isShow: Boolean) {
        with(binding) {
            tvPurchasePrice.isVisible = isShow
            edtPurchasePrice.isVisible = isShow
        }
    }

    private fun clearFocusAndHideSoftInput(vararg views: View) {
        views.forEach { it.clearFocus() }
        val context = requireContext()
        views.forEach { context.hideSoftInput(it) }
    }

    private fun validateInputs(
        productName: String,
        isSell: Boolean,
        sellingPrice: String,
        isBuy: Boolean,
        purchasePrice: String
    ): Boolean {
        when {
            productName.isEmpty() -> {
                binding.edtProductName.apply {
                    error = getString(R.string.empty_field_product_name)
                    requestFocus()
                }
                return false
            }

            isSell && sellingPrice.isEmpty() -> {
                binding.edtSellingPrice.apply {
                    error = getString(R.string.empty_field_selling_price)
                    requestFocus()
                }
                return false
            }

            isBuy && purchasePrice.isEmpty() -> {
                binding.edtPurchasePrice.apply {
                    error = getString(R.string.empty_field_purchase_price)
                    requestFocus()
                }
                return false
            }
        }
        return true
    }

    private fun saveProductData(productData: ProductData) {
        productViewModel.insertProduct(productData)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_save -> {
                binding.apply {
                    val productName = edtProductName.text.toString().trim()
                    val isSell = cbSell.isChecked
                    val isBuy = cbBuy.isChecked
                    val sellingPrice = edtSellingPrice.text.toString().trim()
                    val purchasePrice = edtPurchasePrice.text.toString().trim()

                    if (validateInputs(productName, isSell, sellingPrice, isBuy, purchasePrice)) {
                        val productData = ProductData(
                            productName = productName,
                            isSell = isSell,
                            isBuy = isBuy,
                            sellingPrice = sellingPrice.toLongOrNull(),
                            purchasePrice = purchasePrice.toLongOrNull()
                        )

                        saveProductData(productData)

                        clearFocusAndHideSoftInput(
                            binding.edtProductName,
                            binding.edtSellingPrice,
                            binding.edtPurchasePrice
                        )

                        showToast(getString(R.string.data_saved_successfully_message))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}