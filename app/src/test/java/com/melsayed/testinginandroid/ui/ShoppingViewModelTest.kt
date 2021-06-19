package com.melsayed.testinginandroid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.melsayed.testinginandroid.MainCoroutineRule
import com.melsayed.testinginandroid.getOrAwaitValueTest
import com.melsayed.testinginandroid.other.Constants
import com.melsayed.testinginandroid.other.Resource
import com.melsayed.testinginandroid.other.Status
import com.melsayed.testinginandroid.repositories.FakeShoppingRepoTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepoTest())
    }

    @Test
    fun `insert shopping item with empty field returns error`() {

        viewModel.validateShoppingItem("aaaa", "", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert shopping item with too long name returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append("s")
            }
        }
        viewModel.validateShoppingItem(string, "22", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert shopping item with too long price returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append("s")
            }
        }
        viewModel.validateShoppingItem("sam", "5", string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert shopping item with too high amount returns error`() {

        viewModel.validateShoppingItem(
            "sam",
            "999999999999999999999999999999999999999999999",
            "0.3"
        )

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert shopping item with valid input returns success`() {

        viewModel.validateShoppingItem(
            "sam",
            "99",
            "0.3"
        )

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)

    }

    @Test
    fun `image url is empty after entering shopping item`(){
        viewModel.setCurImgUrl("ssss")
        viewModel.validateShoppingItem("s","5","5.0")
        val value = viewModel.curImgUrl.getOrAwaitValueTest()

        assertThat(value).isEqualTo("")

    }

    @Test
    fun `image url is added after setting it`(){
        viewModel.setCurImgUrl("ssss")
        val value = viewModel.curImgUrl.getOrAwaitValueTest()
        assertThat(value).isEqualTo("ssss")

    }
}