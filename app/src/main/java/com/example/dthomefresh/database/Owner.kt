package com.example.dthomefresh.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO complete these databases
@Entity(tableName = "owners_table")
data class Owner(
    @PrimaryKey(autoGenerate = true)
    var ownerId: Long = 0L,

    @ColumnInfo(name = "owner_name")
    var ownerName: String = "",

    @ColumnInfo(name = "owner_address")
    var ownerAddress: String = "",

    @ColumnInfo(name = "owner_contact")
    var ownerContact: Long = 0L,

    @ColumnInfo(name = "owner_list_of_items_id")
    var itemIds: List<Long>

)


