package com.melsayed.testinginandroid.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.melsayed.testinginandroid.data.local.ShoppingItem
import com.melsayed.testinginandroid.data.remote.PixabayPhoto
import com.melsayed.testinginandroid.other.Resource
import com.melsayed.testinginandroid.retrofit.PixabayResponse

class FakeShoppingRepoAndroidTest : ShoppingRepo {

    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItem = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    private fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableShoppingItem.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
//        var price = 0f
//        for (i in shoppingItems) {
//            price += i.amount * i.price
//        }
//        return price
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return observableShoppingItem
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchPhoto(
        query: String,
        page: Int,
        per_page: Int
    ): Resource<PixabayResponse> {
        return if (shouldReturnNetworkError) {
            Resource.success(PixabayResponse(listOf()))
        }else {
            Resource.error("Error",null)
        }
    }


}