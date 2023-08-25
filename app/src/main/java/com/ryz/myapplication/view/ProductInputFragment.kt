package com.ryz.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private val args: ProductInputFragmentArgs by navArgs()
    private var productData: ProductData? = null
    private var isEdit = false

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

        setupToolbar()
        setupMenu()
        setupCheckBoxes()

        productData = args.detailData
        if (productData != null) {
            isEdit = true
        } else {
            productData = ProductData()
        }

        val btnTitle =
            if (isEdit) getString(R.string.update_title) else getString(R.string.save_title)
        binding.btnSave.apply {
            text = btnTitle
            setOnClickListener(this@ProductInputFragment)
        }

        if (isEdit) getData(args.detailData)
    }

    private fun getData(productData: ProductData?) {
        binding.apply {
            productData?.let {
                edtProductName.setText(it.productName)
                cbSell.isChecked = it.isSell == true
                cbBuy.isChecked = it.isBuy == true
                edtSellingPrice.setText(it.sellingPrice?.toString() ?: "")
                edtPurchasePrice.setText(it.purchasePrice?.toString() ?: "")
            }
        }
    }

    private fun setupToolbar() {
        binding.layoutToolbars.topAppBar.customToolbar(
            activity as MainActivity,
            title = getString(R.string.product_input_title),
            isShowMenu = true,
            menuId = R.menu.product_list
        )
    }

    private fun setupMenu() {
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

    private fun setupCheckBoxes() {
        binding.cbSell.setOnCheckedChangeListener { _, isChecked ->
            showHidePrice(binding.tvSellingPrice, binding.edtSellingPrice, isChecked)
        }

        binding.cbBuy.setOnCheckedChangeListener { _, isChecked ->
            showHidePrice(binding.tvPurchasePrice, binding.edtPurchasePrice, isChecked)
        }
    }

    private fun showHidePrice(titleView: TextView, priceView: TextView, isShouldShow: Boolean) {
        titleView.isVisible = isShouldShow
        priceView.isVisible = isShouldShow
    }

    private fun clearFocusAndHideSoftInput(vararg views: View) {
        val context = requireContext()
        views.forEach {
            it.clearFocus()
            context.hideSoftInput(it)
        }
    }

    private fun saveProductData(productData: ProductData) {
        productViewModel.insertProduct(productData)
        showToast(getString(R.string.data_saved_successfully_message))
    }

    private fun updateProduct(productData: ProductData) {
        productViewModel.updateProduct(productData)
        showToast(getString(R.string.data_updated_successfully_message))
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

                    when {
                        productName.isEmpty() -> {
                            edtProductName.apply {
                                error = getString(R.string.empty_field_product_name)
                                requestFocus()
                            }
                        }

                        isSell && sellingPrice.isEmpty() -> {
                            edtSellingPrice.apply {
                                error = getString(R.string.empty_field_selling_price)
                                requestFocus()
                            }
                        }

                        isBuy && purchasePrice.isEmpty() -> {
                            edtPurchasePrice.apply {
                                error = getString(R.string.empty_field_purchase_price)
                                requestFocus()
                            }
                        }

                        else -> {
                            productData?.let {
                                it.productName = productName
                                it.isSell = isSell
                                it.isBuy = isBuy
                                it.sellingPrice = sellingPrice.toLongOrNull()
                                it.purchasePrice = purchasePrice.toLongOrNull()
                            }

                            if (isEdit) {
                                updateProduct(productData as ProductData)
                            } else {
                                saveProductData(productData as ProductData)

                                clearFocusAndHideSoftInput(
                                    binding.edtProductName,
                                    binding.edtSellingPrice,
                                    binding.edtPurchasePrice
                                )
                            }
                        }
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