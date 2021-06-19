package com.melsayed.testinginandroid

object RegistrationUtil {
    private val existingUsers = listOf("Peter","Carl")


    /**
     * the input is not valid if...
     * ...the username/password is empty
     * ...the username is already taken
     * ...confirmed password is not the same as the password
     * ...password contains less than 2 digits
     */

    fun validateRegistrationInput(
        username:String,
        password:String,
        confirmedPassword:String
    ):Boolean {
        return when {
            username.isEmpty() -> false
            password.isEmpty() -> false
            password != confirmedPassword -> false
            username in existingUsers -> false
            password.count { it.isDigit() } < 2 -> false
            else -> true
        }
    }
}