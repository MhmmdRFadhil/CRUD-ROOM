package com.ryz.myapplication.model.repository

import android.app.Application
import com.ryz.myapplication.model.local.dao.ProductDao
import com.ryz.myapplication.model.local.db.ProductDatabase
import com.ryz.myapplication.model.local.entity.ProductData

class ProductRepository(app: Application) {
    private val productDao: ProductDao

    init {
        val db = ProductDatabase.getDatabase(app)
        productDao = db.getProductDao()
    }

    suspend fun insertProduct(productData: ProductData) =
        productDao.insertProduct(productData)

    fun getAllProduct() = productDao.getAllData()
    suspend fun updateProduct(productData: ProductData) =
        productDao.updateProduct(productData)

    suspend fun deleteProduct(productData: ProductData) =
        productDao.deleteProduct(productData)
}