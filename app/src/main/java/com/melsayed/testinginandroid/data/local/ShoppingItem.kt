package com.melsayed.testinginandroid.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val amount: Int,
    val price: Float,
    val imgUrl: String
)
