package dev.kerscher.openlibraryandroid

import dev.kerscher.openlibraryandroid.data.source.network.ApiService
import dev.kerscher.openlibraryandroid.data.source.network.DefaultBookNetworkDataSource
import dev.kerscher.openlibraryandroid.util.getMockBookResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class DefaultBookNetworkDataSourceUnitTests {

    private val mockApiService: ApiService = mockk()
    private lateinit var dataSource: DefaultBookNetworkDataSource

    @Before
    fun setup() {
        dataSource = DefaultBookNetworkDataSource(mockApiService)
    }

    @Test
    fun `fetchBooks should return mapped BookResponse`() = runTest {
        // Arrange: Mock API response
        val mockResponse = getMockBookResponse()
        coEvery { mockApiService.getLatestBooks() } returns mockResponse

        // Act: Fetch books
        val result = dataSource.fetchBooks("first_publish_year:[2024 TO 2025]")

        // Assert: Verify response is correct
        assertEquals(2, result.numFound)
        assertEquals("Never Flinch", result.books[0].title)
        assertEquals("When the Moon Hits Your Eye", result.books[1].title)

        // Verify API call was made
        coVerify { mockApiService.getLatestBooks() }
    }

    @Test
    fun `fetchBooks should handle exceptions gracefully`() = runTest {
        // Arrange: Mock API throwing exception
        coEvery { mockApiService.getLatestBooks() } throws Exception("Network Error")

        // Act & Assert: Verify exception is propagated
        try {
            dataSource.fetchBooks("first_publish_year:[2024 TO 2025]")
            fail("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals("Network Error", e.message)
        }

        // Verify API call was made
        coVerify { mockApiService.getLatestBooks() }
    }
}