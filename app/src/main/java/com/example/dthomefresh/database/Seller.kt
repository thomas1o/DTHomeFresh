package com.example.dthomefresh.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO complete these databases
@Entity(tableName = "sellers_table")
data class Seller(
    @PrimaryKey(autoGenerate = true)
    var sellerId: Long = 0L,

    @ColumnInfo(name = "seller_name")
    var sellerName: String = "",

    @ColumnInfo(name = "seller_address")
    var sellerAddress: String = "",

    @ColumnInfo(name = "seller_contact")
    var sellerContact: Long = 0L,

    @ColumnInfo(name = "seller_list_of_items_id")
    var itemIds: List<Long>
)


