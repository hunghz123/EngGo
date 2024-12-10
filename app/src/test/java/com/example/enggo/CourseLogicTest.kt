package com.example.enggo.ui.course

import com.example.enggo.ui.course.CourseScreen
import com.example.enggo.ui.course.navigation.navigateToCourses
import com.example.enggo.ui.course.CourseViewModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import com.example.enggo.ui.course.LevelTitles
import com.example.enggo.model.course.Course
import com.example.enggo.data.service.CourseService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.*
import org.junit.Assert.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.mockito.Mock  // Thêm import này
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class CourseLogicTest {

    @Test
    fun testLevelTitlesEnum1() {
        // Kiểm tra xem các giá trị của enum có đúng hay không
        val elementary = LevelTitles.ELEMENTARY
        assertEquals(1, elementary.level)
        assertEquals("Elementary Courses", elementary.title)
    }

    @Test
    fun testLevelTitlesEnum2() {
        // Kiểm tra xem các giá trị của enum có đúng hay không
        val elementary = LevelTitles.PRE_INTERMEDIATE
        assertEquals(2, LevelTitles.PRE_INTERMEDIATE.level)
        assertEquals("Pre-Intermediate Courses", LevelTitles.PRE_INTERMEDIATE.title)
    }

    @Test
    fun testLevelTitlesEnum3() {
        // Kiểm tra xem các giá trị của enum có đúng hay không
        val elementary = LevelTitles.INTERMEDIATE
        assertEquals(3, LevelTitles.INTERMEDIATE.level)
        assertEquals("Intermediate Courses", LevelTitles.INTERMEDIATE.title)
    }

    @Test
    fun testLevelTitlesEnum4() {
        // Kiểm tra xem các giá trị của enum có đúng hay không
        val elementary = LevelTitles.ELEMENTARY
        assertEquals(6, LevelTitles.ADVANCED.level)
        assertEquals("Advanced Courses", LevelTitles.ADVANCED.title)
    }

    @Test
    fun testLevelTitlesEnum5() {
        // Kiểm tra xem các giá trị của enum có đúng hay không
        val elementary = LevelTitles.UPPER_INTERMEDIATE
        assertEquals(5, LevelTitles.UPPER_INTERMEDIATE.level)
        assertEquals("Upper-Intermediate Courses", LevelTitles.UPPER_INTERMEDIATE.title)

    }

    @Test
    fun testLevelTitlesEnum6() {
        // Kiểm tra xem các giá trị của enum có đúng hay không
        val elementary = LevelTitles.ADVANCED
        assertEquals(6, LevelTitles.ADVANCED.level)
        assertEquals("Advanced Courses", elementary.title)
    }

    @Test
    fun testLevelTitlesEnum7() {
        // Kiểm tra xem các giá trị của enum có đúng hay không
        val elementary = LevelTitles.IELTS
        assertEquals(7, LevelTitles.IELTS.level)
        assertEquals("IELTS Courses", elementary.title)
    }

    @Test
    fun testOnItemClick() {
        val mockCourseId = 1
        var clickedCourseId: Int? = null

        val onItemClick: (Int) -> Unit = { courseId ->
            clickedCourseId = courseId
        }

        onItemClick(mockCourseId)
        assertEquals(mockCourseId, clickedCourseId)
    }

    @Test
    fun testCourseImageIndexCalculation() {
        val courseId = 15
        val imagesSize = 10
        val expectedIndex = courseId % imagesSize
        assertEquals(5, expectedIndex)
    }

    @Mock
    private lateinit var courseService: CourseService

    @Mock
    private lateinit var firestore: FirebaseFirestore

    @Mock
    private lateinit var collectionReference: CollectionReference

    private lateinit var viewModel: CourseViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        `when`(courseService.getAllCourses()).thenReturn(collectionReference)

        viewModel = CourseViewModel(courseService)
    }

    @Test
    fun testInitialCoursesStateIsEmpty() = runBlocking {
        val initialCourses = viewModel.courses.first()
        assertTrue(initialCourses.isEmpty())
    }
}
