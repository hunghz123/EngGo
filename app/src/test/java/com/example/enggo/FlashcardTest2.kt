package com.example.enggo

import com.example.enggo.model.Flashcard
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class FlashcardTest2 {

    @Test
    fun testFlashcardWithEmptyValues() {
        val flashcard = Flashcard("", "")
        // Kiểm tra cả hai mặt là rỗng
        assertEquals("", flashcard.FirstCard)
        assertEquals("", flashcard.SecondCard)
    }

    @Test
    fun testFlashcardWithSpecialCharacters() {
        val flashcard = Flashcard("@#\$%", "&*()")

        // Kiểm tra ký tự đặc biệt được lưu trữ chính xác
        assertEquals("@#\$%", flashcard.FirstCard)
        assertEquals("&*()", flashcard.SecondCard)
    }

    @Test
    fun testFlashcardWithIdenticalSides() {
        val flashcard = Flashcard("SameSide", "SameSide")

        // Kiểm tra cả hai mặt giống nhau
        assertEquals("SameSide", flashcard.FirstCard)
        assertEquals("SameSide", flashcard.SecondCard)

        // Giả lập hành động đổi mặt (cả hai mặt giống nhau nên không có thay đổi thực sự)
        var currentSide = flashcard.FirstCard
        currentSide = flashcard.SecondCard
        assertEquals("SameSide", currentSide)
    }

    @Test
    fun testFlashcardToggleMultipleTimes() {
        val flashcard = Flashcard("Front", "Back")

        // Kiểm tra chuyển đổi qua lại giữa hai mặt nhiều lần
        var currentSide = flashcard.FirstCard
        assertEquals("Front", currentSide)

        // Đổi mặt lần 1
        currentSide = flashcard.SecondCard
        assertEquals("Back", currentSide)

        // Đổi mặt lần 2
        currentSide = flashcard.FirstCard
        assertEquals("Front", currentSide)

        // Đổi mặt lần 3
        currentSide = flashcard.SecondCard
        assertEquals("Back", currentSide)
    }
}
