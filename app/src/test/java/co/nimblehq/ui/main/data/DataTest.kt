package co.nimblehq.ui.main.data

import org.junit.Test

import org.junit.Assert.*

@Suppress("IllegalIdentifier")
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
