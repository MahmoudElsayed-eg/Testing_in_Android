package com.melsayed.testinginandroid

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class HomeworkTest {
    @Test
    fun `fib equals 0`() {
        val result = Homework.fib(0)
        assertThat(result).isEqualTo(0)
    }
    @Test
    fun `fib equals 1`() {
        val result = Homework.fib(1)
        assertThat(result).isEqualTo(1)
    }
    @Test
    fun `fib equals fib (n - 2) + fib (n-1)`() {
        val result = Homework.fib(8)
        val fibs = Homework.fib(6) + Homework.fib(7)
        assertThat(result).isEqualTo(fibs)
    }
    @Test
    fun `braces correct`() {
        val result = Homework.checkBraces("()")
        assertThat(result).isTrue()
    }
    @Test
    fun `braces incorrect`() {
        val result = Homework.checkBraces("())))")
        assertThat(result).isFalse()
    }

}