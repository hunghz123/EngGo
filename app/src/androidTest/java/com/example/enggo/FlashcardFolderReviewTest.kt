package com.example.enggo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.example.enggo.model.Flashcard
import com.example.enggo.model.FlashcardFolder
import com.example.enggo.ui.flashcard.FlashcardFolderView
import org.junit.Rule
import org.junit.Test

class FlashcardFolderReviewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFolderInfoDisplay() {
        // Tạo folder và thêm flashcard
        val folder = FlashcardFolder("Test Folder").apply {
            addFlashcard(Flashcard("Hello", "Xin chào"))
        }

        // Hiển thị giao diện
        composeTestRule.setContent {
            FlashcardFolderView(fcFolder = folder)
        }

        // Kiểm tra tên thư mục hiển thị đúng
        composeTestRule.onNodeWithText("Test Folder").assertExists()

        // Kiểm tra số lượng flashcard hiển thị đúng
        composeTestRule.onNodeWithText("1 / 1").assertExists()
    }

    @Test
    fun testFlashcardListDisplay() {
        // Tạo folder và thêm flashcards
        val folder = FlashcardFolder("Test Folder").apply {
            addFlashcard(Flashcard("Hello", "Xin chào"))
            addFlashcard(Flashcard("Goodbye", "Tạm biệt"))
        }

        // Hiển thị giao diện
        composeTestRule.setContent {
            FlashcardFolderView(fcFolder = folder)
        }

        // Kiểm tra hiển thị đúng các flashcard
        composeTestRule.onNodeWithText("Hello").assertExists()
        composeTestRule.onNodeWithText("Xin chào").assertExists()

        composeTestRule.onNodeWithText("Goodbye").assertExists()
        composeTestRule.onNodeWithText("Tạm biệt").assertExists()
    }
}
