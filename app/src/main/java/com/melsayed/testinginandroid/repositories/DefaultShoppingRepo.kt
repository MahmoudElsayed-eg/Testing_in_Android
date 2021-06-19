package com.melsayed.testinginandroid.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.melsayed.testinginandroid.data.PixabayPagingSource
import com.melsayed.testinginandroid.data.local.ShoppingDao
import com.melsayed.testinginandroid.data.local.ShoppingItem
import com.melsayed.testinginandroid.data.remote.PixabayPhoto
import com.melsayed.testinginandroid.other.Resource
import com.melsayed.testinginandroid.retrofit.PixabayApi
import com.melsayed.testinginandroid.retrofit.PixabayResponse
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepo @Inject constructor(
    private val api: PixabayApi,
    private val dao: ShoppingDao
) : ShoppingRepo {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        dao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        dao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return dao.getAll()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return dao.getTotalPrice()
    }

    fun searchPhoto(query: String): LiveData<PagingData<PixabayPhoto>> {
        return Pager(
            config = PagingConfig(pageSize = 20,enablePlaceholders = false,maxSize = 100),
            pagingSourceFactory = {PixabayPagingSource(api,query)}
        ).liveData
    }

    override suspend fun searchPhoto(
        query: String,
        page: Int,
        per_page: Int
    ): Resource<PixabayResponse> {
        return try {
            val response = api.searchPhotos(query,page,per_page)
                response.let {
                    return@let Resource.success(it)
                } ?: Resource.error("error",null)

        }catch (e:Exception) {
            Resource.error("no internet",null)
        }
    }
}