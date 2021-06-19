package com.melsayed.testinginandroid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.melsayed.testinginandroid.R
import com.melsayed.testinginandroid.adapters.ImagesPagerAdapter
import com.melsayed.testinginandroid.data.PixabayPagingSource
import com.melsayed.testinginandroid.data.remote.PixabayPhoto
import com.melsayed.testinginandroid.getOrAwaitValue
import com.melsayed.testinginandroid.launchFragmentInHiltContainer
import com.melsayed.testinginandroid.repositories.FakeShoppingRepoAndroidTest
import com.melsayed.testinginandroid.retrofit.PixabayApi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import javax.inject.Inject

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickFragTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl() = runBlockingTest {
        val navController = mock(NavController::class.java)
        val data = PagingData.from(listOf(PixabayPhoto("","Test")))
        val viewModel1 = ShoppingViewModel(FakeShoppingRepoAndroidTest())
        launchFragmentInHiltContainer<ImagePickFrag>() {
            launch {
                Navigation.setViewNavController(requireView(), navController)
                imageAdapter.submitData(data)
                viewModel = viewModel1
            }
        }
        onView(withId(R.id.rv_search)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImagesPagerAdapter.ImageItemViewHolder>(
                0,
                click()
            )
        )
        verify(navController).popBackStack()
        val value = viewModel1.curImgUrl.getOrAwaitValue()
        assertThat(value).isEqualTo("Test")
    }

}