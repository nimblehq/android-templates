package co.nimblehq.ui.screens.data

import co.nimblehq.ui.screens.home.Data
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DataTest {

    val testData = Data("content", "url")

    @Test
    fun getContentTest() {
        assertTrue(testData.content == "content")
    }

    @Test
    fun getImageUrlTest() {
        assertTrue(testData.imageUrl == "url")
    }

    @Test
    fun copyTest() {
        val copy = testData.copy(content = "content2")
        assertTrue(copy.content == "content2" && copy.imageUrl == "url")
    }

    @Test
    fun equalsTest() {
        val another = Data("content", "url")
        assertEquals(testData, another)
    }
}
