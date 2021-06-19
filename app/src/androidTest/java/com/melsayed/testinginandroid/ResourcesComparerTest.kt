package com.melsayed.testinginandroid

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ResourcesComparerTest {

    private lateinit var resourcesComparer: ResourcesComparer

    @Before
    fun setup() {
        resourcesComparer = ResourcesComparer()
    }

    @Test
    fun stringResourceSameAsGivenString_returnTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourcesComparer.isEqual(context, R.string.app_name, "Testing in Android")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceNotSameAsGivenString_returnFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourcesComparer.isEqual(context, R.string.app_name, "TestinginAndroid")
        assertThat(result).isFalse()
    }
}