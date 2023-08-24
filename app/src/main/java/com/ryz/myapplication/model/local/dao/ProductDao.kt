package com.ryz.myapplication.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import com.ryz.myapplication.model.local.entity.ProductData

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productData: ProductData)

    @Query("SELECT * FROM PRODUCT_ENTITY")
    fun getAllData(): LiveData<List<ProductData>>

    @Update
    suspend fun updateProduct(productData: ProductData)

    @Delete
    suspend fun deleteProduct(productData: ProductData)

}