package com.example.enggo.data.service

import com.example.enggo.ui.register.RegisterScreen
import org.junit.Assert.*
import org.junit.Test


class RegisterLogicTest {

    @Test
    fun testIsValidUsername_invalidUsername() {
        val username = "invalid@username"
        assertFalse(isValidUsername(username))  // Kiểm tra tên người dùng không hợp lệ
    }

    // Hàm kiểm tra tính hợp lệ của tên người dùng
    private fun isValidUsername(username: String): Boolean {
        return username.matches("^[a-zA-Z0-9_]+$".toRegex()) && username.length >= 3
    }


    @Test
    fun testIsValidEmail_invalidEmail() {
        val email = "user@com"
        assertFalse(isValidEmail(email))  // Kiểm tra email không hợp lệ
    }

    // Hàm kiểm tra tính hợp lệ của email
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    @Test
    fun testIsValidPassword_invalidPassword() {
        val password = "12345"
        assertFalse(isValidPassword(password))  // Kiểm tra mật khẩu không hợp lệ
    }

    // Hàm kiểm tra tính hợp lệ của mật khẩu
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && password.any { it.isDigit() } && password.any { it.isLetter() }
    }


}

