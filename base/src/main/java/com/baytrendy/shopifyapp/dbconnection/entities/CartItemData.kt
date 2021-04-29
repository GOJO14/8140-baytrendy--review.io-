package com.baytrendy.shopifyapp.dbconnection.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.io.Serializable

@Entity
class CartItemData : Serializable {
    @PrimaryKey
    @ColumnInfo(name = "variant_id")
    lateinit var variant_id: String
    @ColumnInfo(name = "qty")
    var qty: Int = 1
}
