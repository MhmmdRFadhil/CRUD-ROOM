package com.ryz.myapplication.model.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "product_entity")
@Parcelize
data class ProductData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "productName")
    var productName: String? = null,

    @ColumnInfo(name = "isSell")
    var isSell: Boolean? = false,

    @ColumnInfo(name = "isBuy")
    var isBuy: Boolean? = false,

    @ColumnInfo(name = "sellingPrice")
    var sellingPrice: Long? = 0,

    @ColumnInfo(name = "purchasePrice")
    var purchasePrice: Long? = 0
) : Parcelable
