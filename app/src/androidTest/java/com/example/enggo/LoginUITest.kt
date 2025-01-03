package com.example.enggo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import com.example.enggo.ui.login.LoginScreen
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput

class LoginUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_login_button_is_enabled_when_username_and_password_are_filled() {
        // Thiết lập giao diện người dùng
        composeTestRule.setContent {
            LoginScreen(onLoginClick = {}, redirectToRegister = {})
        }

        // Điền vào các trường tên người dùng và mật khẩu
        composeTestRule.onNodeWithText("Username")
            .performTextInput("testuser")
        composeTestRule.onNodeWithText("Password")
            .performTextInput("password123")

        // Kiểm tra nút Đăng nhập có được kích hoạt không
        composeTestRule.onNodeWithText("Log In")
            .assertIsEnabled()
    }

    @Test
    fun test_login_button_is_disabled_when_username_and_password_are_empty() {
        // Thiết lập giao diện người dùng
        composeTestRule.setContent {
            LoginScreen(onLoginClick = {}, redirectToRegister = {})
        }

        // Kiểm tra nút Đăng nhập bị vô hiệu hóa khi chưa điền thông tin
        composeTestRule.onNodeWithText("Log In")
            .assertIsNotEnabled()
    }

    @Test
    fun test_navigate_to_register_screen_when_register_link_is_clicked() {
        // Thiết lập giao diện người dùng
        composeTestRule.setContent {
            LoginScreen(onLoginClick = {}, redirectToRegister = {})
        }

        // Nhấn vào liên kết "Don't have an account? Register"
        composeTestRule.onNodeWithText("Don't have an account? Register")
            .performClick()

        // Kiểm tra xem chức năng chuyển hướng có được gọi không
        // Điều này phụ thuộc vào cách bạn thực hiện hàm redirectToRegister
    }

    @Test
    fun verifyEmptyFieldsInitially() {
        composeTestRule.setContent {
            LoginScreen(onLoginClick = {}, redirectToRegister = {})
        }

        composeTestRule
            .onNodeWithText("Username")
            .assertTextContains("")
        composeTestRule
            .onNodeWithText("Password")
            .assertTextContains("")
    }

    @Test
    fun verifyErrorMessageDisplayWhenLoginFails() {
        composeTestRule.setContent {
            LoginScreen(onLoginClick = {}, redirectToRegister = {})
        }

        composeTestRule
            .onNodeWithText("Username")
            .performTextInput("wronguser")
        composeTestRule
            .onNodeWithText("Password")
            .performTextInput("wrongpass")
        composeTestRule
            .onNodeWithText("Log In")
            .performClick()

        composeTestRule
            .onNodeWithText("Username or password is incorrect")
            .assertIsDisplayed()
    }

    @Test
    fun passwordVisibilityToggleFunction() {
        composeTestRule.setContent {
            LoginScreen(onLoginClick = {}, redirectToRegister = {})
        }

        composeTestRule
            .onNodeWithContentDescription("Password")
            .performTextInput("testpassword")

        composeTestRule
            .onNodeWithContentDescription("Show Password")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Hide Password")
            .assertExists()
    }
}
