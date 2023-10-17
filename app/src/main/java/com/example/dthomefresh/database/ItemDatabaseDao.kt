package com.example.dthomefresh.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDatabaseDao {

    @Insert
    fun insertItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Query("Delete From items_table Where itemId = :key")
    fun deleteItem(key: Long)

    @Query("Select * From items_table Where itemId = :key")
    fun get(key: Long): Item

    @Query("SELECT * FROM items_table ORDER BY owner_id ASC")
    fun getAllNights(): LiveData<List<Item>>

}