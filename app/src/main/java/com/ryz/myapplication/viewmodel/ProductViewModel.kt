package com.ryz.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryz.myapplication.model.local.entity.ProductData
import com.ryz.myapplication.model.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(app: Application) : ViewModel() {
    private val productRepository: ProductRepository = ProductRepository(app)

    fun insertProduct(productData: ProductData) = viewModelScope.launch {
        productRepository.insertProduct(productData)
    }

    fun getAllProduct() = productRepository.getAllProduct()

    fun updateProduct(productData: ProductData) = viewModelScope.launch {
        productRepository.updateProduct(productData)
    }

    fun deleteProduct(productData: ProductData) = viewModelScope.launch {
        productRepository.deleteProduct(productData)
    }
}