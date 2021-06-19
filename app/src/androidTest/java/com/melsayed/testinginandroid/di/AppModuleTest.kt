package com.melsayed.testinginandroid.di

import android.content.Context
import androidx.room.Room
import com.melsayed.testinginandroid.data.local.ShoppingItemDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModuleTest {
    @Provides
    @Named("test_db")
    fun providesDb(@ApplicationContext context: Context): ShoppingItemDB =
        Room.inMemoryDatabaseBuilder(context, ShoppingItemDB::class.java).allowMainThreadQueries()
            .build()
}