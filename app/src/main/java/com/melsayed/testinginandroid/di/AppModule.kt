package com.melsayed.testinginandroid.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.melsayed.testinginandroid.R
import com.melsayed.testinginandroid.data.local.ShoppingDao
import com.melsayed.testinginandroid.data.local.ShoppingItemDB
import com.melsayed.testinginandroid.other.Constants.DATABASE_NAME
import com.melsayed.testinginandroid.repositories.DefaultShoppingRepo
import com.melsayed.testinginandroid.repositories.ShoppingRepo
import com.melsayed.testinginandroid.retrofit.PixabayApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDB(@ApplicationContext context: Context): ShoppingItemDB =
        Room.databaseBuilder(context, ShoppingItemDB::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun providesDao(shoppingItemDB: ShoppingItemDB): ShoppingDao = shoppingItemDB.shoppingDao()

    @Provides
    @Singleton
    fun providesGlideInstance(@ApplicationContext context: Context) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_error)
            .error(R.drawable.ic_error)
            .centerCrop()
    )
    @Provides
    @Singleton
    fun providesApi(): PixabayApi = Retrofit.Builder().baseUrl(PixabayApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create(PixabayApi::class.java)




    @Provides
    @Singleton
    fun providesRepo(dao: ShoppingDao,api: PixabayApi) = DefaultShoppingRepo(api,dao) as ShoppingRepo
}