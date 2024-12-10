package com.example.enggo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.example.enggo.model.FlashcardFolder
import org.junit.Rule
import org.junit.Test
import com.example.enggo.ui.flashcard.createFCFolderScreen

class FlashcardFolderCreateScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCreateFolderScreen_AddTerm() {
        composeTestRule.setContent {
            createFCFolderScreen(fcFolder = FlashcardFolder())
        }

        // Kiểm tra nút "Add Term" tồn tại
        composeTestRule.onNodeWithText("Add Term").assertExists()

        // Nhấn nút "Add Term"
        composeTestRule.onNodeWithText("Add Term").performClick()

        // Kiểm tra xem thuật ngữ mới được thêm
        composeTestRule.onAllNodesWithText("TERM").assertCountEquals(3) // Mặc định có 2, thêm 1 sẽ thành 3
    }

    @Test
    fun testCreateFolderScreen_ChangeFolderName() {
        composeTestRule.setContent {
            createFCFolderScreen(fcFolder = FlashcardFolder())
        }

        // Tìm trường nhập liệu "Folder name" và nhập tên mới
        composeTestRule.onNodeWithText("Folder name").performTextInput("My New Folder")

        // Kiểm tra tên mới đã được hiển thị
        composeTestRule.onNodeWithText("My New Folder").assertExists()
    }
}
