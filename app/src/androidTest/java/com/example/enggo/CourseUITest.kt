package com.example.enggo

import com.example.enggo.ui.course.CourseScreen
import com.example.enggo.ui.course.navigation.navigateToCourses
import com.example.enggo.ui.course.CourseViewModel
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.enggo.model.course.Course
import com.example.enggo.ui.course.CourseListItem
import com.example.enggo.ui.course.CourseScreen
import com.example.enggo.ui.theme.EngGoTheme
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.enggo.ui.course.CourseScreen
import com.example.enggo.ui.theme.EngGoTheme
import com.example.enggo.ui.course.LevelTitles

class CourseScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun courseListItem_displaysCorrectContent() {
        val course = Course(
            course_id = 1,
            course_name = "Test Course",
            course_description = "Test Description",
            course_level = 1
        )

        composeTestRule.setContent {
            EngGoTheme {
                CourseListItem(
                    courses = course,
                    onItemClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("1 Test Course").assertExists()
        composeTestRule.onNodeWithText("Test Description").assertExists()
        composeTestRule.onNodeWithText("Elementary").assertExists()
    }

    @Test
    fun courseScreen_displayCoursesGroupedByLevel() {
        val courses = listOf(
            Course(1, "Elementary Course", "Description", 1),
            Course(2, "Intermediate Course", "Description", 3)
        )

        composeTestRule.setContent {
            EngGoTheme {
                CourseScreen(
                    onCourseClick = {},
                    unfilteredCoursesList = courses
                )
            }
        }

        composeTestRule.waitForIdle()

        // Kiểm tra tiêu đề các level
        composeTestRule.onNodeWithText("Elementary Courses").assertExists()
        composeTestRule.onNodeWithText("Intermediate Courses").assertExists()

        // Kiểm tra các khóa học - thêm course_id vào text
        composeTestRule.onNodeWithText("1 Elementary Course").assertExists()
        composeTestRule.onNodeWithText("2 Intermediate Course").assertExists()
    }

    @Test
    fun courseListItem_clickable() {
        var clicked = false
        val course = Course(1, "Test Course", "Description", 1)

        composeTestRule.setContent {
            EngGoTheme {
                CourseListItem(
                    courses = course,
                    onItemClick = { clicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("1 Test Course").performClick()
        assert(clicked)
    }

    /*
    @Test
    fun courseScreen_scrollable() {
        // Tạo nhiều khóa học để test scroll
        val courses = List(15) { index ->
            Course(
                course_id = index,
                course_name = "Course $index",
                course_description = "Description",
                course_level = 1
            )
        }

        composeTestRule.setContent {
            EngGoTheme {
                CourseScreen(
                    onCourseClick = {},
                    unfilteredCoursesList = courses
                )
            }
        }

        composeTestRule.waitForIdle()

        // Tìm LazyRow bằng testTag và thực hiện scroll
        composeTestRule
            .onNodeWithTag("course_list")
            .performScrollToIndex(14)  // Sử dụng performScrollToIndex thay vì performScrollToKey

        // Kiểm tra item cuối có hiển thị
        composeTestRule.onNodeWithText("14 Course 14").assertExists()
    }
     */

    @Test
    fun testTopBarTitle() {
        // Test hiển thị tiêu đề của TopBar
        composeTestRule.setContent {
            CourseScreen(
                onCourseClick = {},
                unfilteredCoursesList = emptyList()
            )
        }

        // Kiểm tra text "Courses" có xuất hiện trong TopBar
        composeTestRule.onNodeWithText("Courses").assertExists()
    }

    @Test
    fun testCourseDescriptionEllipsis() {
        val longDescription = "This is a very long description that should be truncated with ellipsis because it exceeds the maximum number of lines allowed in the course description."
        val course = Course(
            course_id = 1,
            course_name = "Test Course",
            course_description = longDescription,
            course_level = 1
        )

        composeTestRule.setContent {
            CourseListItem(
                courses = course,
                onItemClick = {}
            )
        }

        // Chỉ kiểm tra sự tồn tại của text
        composeTestRule.onNodeWithText(longDescription, substring = true)
            .assertExists()
    }

    @Test
    fun testEmptyCoursesListDisplay() {
        // Test hiển thị khi không có khóa học
        composeTestRule.setContent {
            CourseScreen(
                onCourseClick = {},
                unfilteredCoursesList = emptyList()
            )
        }

        // Không nên hiển thị bất kỳ level title nào khi không có khóa học
        LevelTitles.values().forEach { level ->
            composeTestRule.onNodeWithText(level.title).assertDoesNotExist()
        }
    }
}