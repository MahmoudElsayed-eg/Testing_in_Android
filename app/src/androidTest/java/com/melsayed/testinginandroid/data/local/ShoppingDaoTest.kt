package com.melsayed.testinginandroid.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.melsayed.testinginandroid.getOrAwaitValue
import com.melsayed.testinginandroid.launchFragmentInHiltContainer
import com.melsayed.testinginandroid.ui.ShoppingFrag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @get:Rule
    var hiltTestRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDB
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltTestRule.inject()
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(1, "ss", 2, 5f, "s")
        dao.insertShoppingItem(shoppingItem)
        val getAllItems = dao.getAll().getOrAwaitValue()
        assertThat(getAllItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(1, "ss", 2, 5f, "s")
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val getAllItems = dao.getAll().getOrAwaitValue()
        assertThat(getAllItems).doesNotContain(shoppingItem)
    }

    @Test
    fun getSumShoppingItems() = runBlockingTest {
        val shoppingItem1 = ShoppingItem(1, "s1s", 5, 20f, "a")
        val shoppingItem2 = ShoppingItem(2, "s2s", 4, 0.25f, "s")
        val shoppingItem3 = ShoppingItem(3, "s3s", 1, 1.40f, "d")
        val shoppingItem4 = ShoppingItem(4, "s4s", 30, 2.2f, "e")
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)
        dao.insertShoppingItem(shoppingItem4)
        val getTotalPrice = dao.getTotalPrice().getOrAwaitValue()
        val result =
            shoppingItem1.amount * shoppingItem1.price + shoppingItem2.amount * shoppingItem2.price + shoppingItem3.amount * shoppingItem3.price + shoppingItem4.amount * shoppingItem4.price
        assertThat(getTotalPrice).isEqualTo(result)
    }

}