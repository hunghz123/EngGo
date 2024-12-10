package com.example.enggo

import com.example.enggo.model.Flashcard
import org.junit.Test
import org.junit.Assert.assertEquals

class FlashcardTest {

    @Test
    fun testFlashcardInitialization() {
        val flashcard = Flashcard("Hello", "Xin chào")
        // Gọi hàm Flashcard() để khởi tạo giá trị

        // Kiểm tra hai mặt của flashcard
        assertEquals("Hello", flashcard.FirstCard)
        assertEquals("Xin chào", flashcard.SecondCard)
    }

    @Test
    fun testFlashcardToggle() {
        val flashcard = Flashcard("Hello", "Xin chào")

        var currentSide = flashcard.FirstCard
        // Kiểm tra mặt đầu tiên
        assertEquals("Hello", currentSide)

        // Giả lập hành động click đổi mặt
        currentSide = flashcard.SecondCard
        // Kiểm tra mặt sau
        assertEquals("Xin chào", currentSide)
    }
}
