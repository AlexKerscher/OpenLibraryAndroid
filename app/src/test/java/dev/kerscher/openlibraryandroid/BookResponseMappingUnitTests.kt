package dev.kerscher.openlibraryandroid

import dev.kerscher.openlibraryandroid.data.source.network.toDomainModel
import dev.kerscher.openlibraryandroid.util.getMockBookResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class BookResponseMappingUnitTests {

    @Test
    fun `BookResponse toDomainModel maps correctly`() {
        // Arrange: Mock BookResponse
        val mockResponse = getMockBookResponse()

        // Act: Map to domain model
        val books = mockResponse.toDomainModel()

        // Assert: Verify mapping
        assertEquals(2, books.size)
        assertEquals("Never Flinch", books[0].title)
        assertEquals("Stephen King", books[0].authors[0])
        assertEquals(448, books[0].numberOfPagesMedian)
    }
}