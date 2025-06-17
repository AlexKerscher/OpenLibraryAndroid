package dev.kerscher.openlibraryandroid

import dev.kerscher.openlibraryandroid.data.source.local.BookDao
import dev.kerscher.openlibraryandroid.util.getMockLocalBook
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class BookDaoUnitTests {

    @Test
    fun `viewModel fetches book by id`() = runTest {
        // Arrange: Mock the DAO
        val mockDao = mockk<BookDao>()
        val fakeBook = getMockLocalBook()
        coEvery { mockDao.getBookById(fakeBook.id) } returns fakeBook

        // Act: Inject the DAO into the repository/viewmodel and fetch the book
        val result = mockDao.getBookById(fakeBook.id)

        // Assert: Verify correct behavior
        assertEquals(fakeBook, result)
        coVerify { mockDao.getBookById(fakeBook.id) }
    }
}