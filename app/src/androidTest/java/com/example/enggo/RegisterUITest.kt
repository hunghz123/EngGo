package com.example.enggo.ui.register

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.enggo.ui.theme.EngGoTheme
import org.junit.Rule
import org.junit.Test
import com.example.enggo.ui.register.RegisterScreen

class RegisterUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Test cho việc nhấn nút "Create an account"
    @Test
    fun testRegisterButtonClick() {
        composeTestRule.setContent {
            EngGoTheme {
                RegisterScreen(onRegisterClick = {}, redirectToLogin = {})
            }
        }

        // Nhập thông tin vào các trường
        composeTestRule.onNodeWithText("Username").performTextInput("TestUser")
        composeTestRule.onNodeWithText("Your Email").performTextInput("testuser@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("password123")

        // Nhấn nút đăng ký
        composeTestRule.onNodeWithText("Create an account").performClick()

        // Kiểm tra lại xem giao diện có hiển thị lại như mong đợi không
        composeTestRule.onNodeWithText("Sign up with Email").assertIsDisplayed()
    }

    // Test cho thông báo tên người dùng đã tồn tại
    @Test
    fun testUsernameAvailabilityMessage() {
        composeTestRule.setContent {
            EngGoTheme {
                RegisterScreen(onRegisterClick = {}, redirectToLogin = {})
            }
        }

        // Nhập tên người dùng đã tồn tại
        composeTestRule.onNodeWithText("Username").performTextInput("ExistingUser")

        // Kiểm tra xem thông báo "Username is available" có hiển thị không
        composeTestRule.onNodeWithText("Username is available").assertExists()
    }

    // Test cho mật khẩu không khớp
    @Test
    fun testPasswordMismatch() {
        composeTestRule.setContent {
            EngGoTheme {
                RegisterScreen(onRegisterClick = {}, redirectToLogin = {})
            }
        }

        // Nhập mật khẩu và xác nhận mật khẩu không khớp
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("password321")

        // Kiểm tra thông báo lỗi "Password is incorrect!"
        composeTestRule.onNodeWithText("Password is incorrect!").assertExists()
    }

}
