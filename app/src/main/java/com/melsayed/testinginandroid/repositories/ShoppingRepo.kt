package com.melsayed.testinginandroid.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.melsayed.testinginandroid.data.local.ShoppingItem
import com.melsayed.testinginandroid.data.remote.PixabayPhoto
import com.melsayed.testinginandroid.other.Resource
import com.melsayed.testinginandroid.retrofit.PixabayResponse

interface ShoppingRepo {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>
    fun observeTotalPrice(): LiveData<Float>
    suspend fun searchPhoto(query: String, page: Int, per_page: Int): Resource<PixabayResponse>
}