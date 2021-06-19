package com.melsayed.testinginandroid

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `valid user and password correct and repeated`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Philip",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }
    @Test
    fun `valid user and password correct and repeated incorrect`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Philip",
            "123",
            "abc"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `exist user and password correct and repeated`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `password is empty`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
            "",
            ""
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `password is less than 2 digits`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
            "sssss2",
            "sssss2"
        )
        assertThat(result).isFalse()
    }
}