package com.melsayed.testinginandroid.data

import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.melsayed.testinginandroid.data.remote.PixabayPhoto
import com.melsayed.testinginandroid.retrofit.PixabayApi
import retrofit2.HttpException
import retrofit2.http.Query
import java.io.IOException

private const val STARTING_PAGE = 1
class PixabayPagingSource(private val pixabayApi: PixabayApi,private val query:String) : PagingSource<Int,PixabayPhoto>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PixabayPhoto> {
        val position = params.key ?: STARTING_PAGE
        return try {
            val response = pixabayApi.searchPhotos(query, position,params.loadSize)
            val photos = response.hits
            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        }catch (e : HttpException){
            LoadResult.Error(e)
        }catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PixabayPhoto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}