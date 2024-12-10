package com.example.enggo

import com.example.enggo.ui.login.LoginScreen
import org.junit.Assert.*
import org.junit.Test

class LoginLogicTest {
    @Test
    fun testIsValidUsername_validUsername() {
        val username = "valid_username123"
        assertTrue(isValidUsername(username))  // Kiểm tra tên người dùng hợp lệ
    }

    // Hàm kiểm tra tính hợp lệ của tên người dùng
    private fun isValidUsername(username: String): Boolean {
        return username.matches("^[a-zA-Z0-9_]+$".toRegex()) && username.length >= 3
    }

    @Test
    fun testIsValidPassword_validPassword() {
        val password = "Password123"
        assertTrue(isValidPassword(password))  // Kiểm tra mật khẩu hợp lệ
    }

    // Hàm kiểm tra tính hợp lệ của mật khẩu
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && password.any { it.isDigit() } && password.any { it.isLetter() }
    }

    @Test
    fun testLogin_validCredentials() {
        val username = "validUsername"
        val password = "CorrectPassword123"
        val result = verifyLoginInfo(username, password)
        assertTrue(result)  // Kiểm tra khi tên người dùng và mật khẩu đúng
    }

    // Giả lập hàm kiểm tra thông tin đăng nhập
    private fun verifyLoginInfo(username: String, password: String): Boolean {
        // Giả sử trong hệ thống có một username là "validUsername" và password là "CorrectPassword123"
        return username == "validUsername" && password == "CorrectPassword123"
    }

    @Test
    fun testPasswordTooShort() {
        val password = "Short1"
        assertFalse(isValidPassword(password))  // Mật khẩu phải dài ít nhất 8 ký tự
    }

}
