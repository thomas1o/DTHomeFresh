package com.example.dthomefresh.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO complete these databases
@Entity(tableName = "items_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,

    @ColumnInfo(name = "owner_id")
    var ownerId: Long = 0L
)


