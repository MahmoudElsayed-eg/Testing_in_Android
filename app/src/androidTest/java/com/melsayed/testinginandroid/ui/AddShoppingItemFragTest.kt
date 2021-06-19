package com.melsayed.testinginandroid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.melsayed.testinginandroid.R
import com.melsayed.testinginandroid.data.local.ShoppingItem
import com.melsayed.testinginandroid.getOrAwaitValue
import com.melsayed.testinginandroid.launchFragmentInHiltContainer
import com.melsayed.testinginandroid.repositories.FakeShoppingRepoAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var viewModel1: ShoppingViewModel


    @Before
    fun setup() {
        viewModel1 = ShoppingViewModel(FakeShoppingRepoAndroidTest())
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFrag> {
            Navigation.setViewNavController(requireView(), navController)
        }
        pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun pressBackButton_makeViewModelEmpty() {
        val navController = mock(NavController::class.java)
        viewModel1.setCurImgUrl("TEST")
        launchFragmentInHiltContainer<AddShoppingItemFrag> {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = viewModel1
        }
        pressBack()
        verify(navController).popBackStack()
        val value = viewModel1.curImgUrl.getOrAwaitValue()
        assertThat(value).isEqualTo("")
    }

    @Test
    fun pressAddButton_insertToDb_popStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFrag> {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = viewModel1
        }
        onView(withId(R.id.etShoppingItemName)).perform(replaceText("Name"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("1.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())
        assertThat(viewModel1.shoppingItems.getOrAwaitValue()).contains(
            ShoppingItem(
                0,
                "Name",
                5,
                1.5f,
                ""
            )
        )
//        verify(navController).popBackStack()
    }


}