package com.melsayed.testinginandroid.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.melsayed.testinginandroid.R
import com.melsayed.testinginandroid.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ShoppingFragTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddShoppingItemFabButton_navigateToAddShoppingFrag() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ShoppingFrag> {
            Navigation.setViewNavController(requireView(),navController)
        }
        onView(withId(R.id.fbAddItem)).perform(click())
        verify(navController).navigate(
            ShoppingFragDirections.actionShoppingFragToAddShoppingItemFrag()
        )
    }
}