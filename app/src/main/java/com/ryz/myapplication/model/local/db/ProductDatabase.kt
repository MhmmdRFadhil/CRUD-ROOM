package com.ryz.myapplication.model.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ryz.myapplication.model.local.dao.ProductDao
import com.ryz.myapplication.model.local.entity.ProductData

@Database(entities = [ProductData::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ProductDatabase {
            if (INSTANCE == null) {
                synchronized(ProductDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "product_database"
                    ).build()
                }
            }
            return INSTANCE as ProductDatabase
        }
    }
}