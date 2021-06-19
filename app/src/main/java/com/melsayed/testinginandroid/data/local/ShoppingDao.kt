package com.melsayed.testinginandroid.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.melsayed.testinginandroid.data.local.ShoppingItem

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun getAll(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(amount * price) FROM shopping_items")
    fun getTotalPrice(): LiveData<Float>
}