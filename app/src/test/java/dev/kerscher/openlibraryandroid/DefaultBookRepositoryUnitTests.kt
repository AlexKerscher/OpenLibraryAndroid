package dev.kerscher.openlibraryandroid

import com.google.common.truth.Truth.assertThat
import dev.kerscher.openlibraryandroid.data.DefaultBookRepository
import dev.kerscher.openlibraryandroid.data.source.local.BookDao
import dev.kerscher.openlibraryandroid.data.source.local.WishlistEntity
import dev.kerscher.openlibraryandroid.data.source.network.BookNetworkDataSource
import dev.kerscher.openlibraryandroid.data.source.network.BookResponse
import dev.kerscher.openlibraryandroid.util.getMockBook1
import dev.kerscher.openlibraryandroid.util.getMockLocalBook
import dev.kerscher.openlibraryandroid.util.getMockNetworkBooks
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultBookRepositoryUnitTests {

    // Mocks
    private lateinit var mockBookDao: BookDao
    private lateinit var mockNetworkDataSource: BookNetworkDataSource
    private lateinit var repository: DefaultBookRepository

    // Test dispatcher for coroutines
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        // Initialize mocks
        mockBookDao = mockk(relaxed = true)
        mockNetworkDataSource = mockk(relaxed = true)

        // Initialize the repository with mocked dependencies
        repository = DefaultBookRepository(
            networkDataSource = mockNetworkDataSource,
            bookDao = mockBookDao,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `getBooksStream maps LocalBooks to DomainBooks`() = runTest {
        // Arrange: Mock DAO to return a flow of local books
        val mockLocalBooks = listOf(getMockLocalBook())
        every { mockBookDao.getAllBooks() } returns flowOf(mockLocalBooks)

        // Act: Collect the flow of books from the repository
        val books = repository.getBooksStream().first()

        // Assert: Verify that the domain model is returned
        assertThat(books).hasSize(1)
        assertThat(books[0]).isEqualTo(getMockBook1())
    }

    @Test
    fun `getBooks with forceUpdate calls refreshBooks`() = runTest {
        // Arrange: Mock DAO and network data source
        val mockLocalBooks = listOf(getMockLocalBook())
        coEvery { mockBookDao.getAllBooksSync() } returns mockLocalBooks
        coEvery { mockNetworkDataSource.fetchBooks(any()) } returns BookResponse(books = getMockNetworkBooks())
        coJustRun { mockBookDao.replaceBooks(any()) }

        // Act: Call getBooks with forceUpdate = true
        repository.getBooks(2025, 2025, forceUpdate = true)

        // Assert: Verify that `fetchBooks` and `replaceBooks` are called inside `refreshBooks`
        coVerify { mockNetworkDataSource.fetchBooks("first_publish_year:[2025 TO 2025]") }
        coVerify { mockBookDao.replaceBooks(match { it.size == 2 }) }
    }

    @Test
    fun `getBooks without forceUpdate skips refreshBooks`() = runTest {
        // Arrange: Mock DAO to return some books
        val mockLocalBooks = listOf(getMockLocalBook())
        coEvery { mockBookDao.getAllBooksSync() } returns mockLocalBooks

        // Act: Call getBooks with forceUpdate = false
        val books = repository.getBooks(2025, 2025, forceUpdate = false)

        // Assert: Verify that `refreshBooks` was not called and books were returned
        coVerify(exactly = 0) { mockNetworkDataSource.fetchBooks(any()) } // No network calls
        assertThat(books).hasSize(1)
        assertThat(books[0]).isEqualTo(getMockBook1())
    }

    @Test
    fun `refreshBooks fetches from network and updates database`() = runTest {
        // Arrange: Mock network response and DAO
        val mockNetworkResponse = BookResponse(books = getMockNetworkBooks())
        coEvery { mockNetworkDataSource.fetchBooks(any()) } returns mockNetworkResponse
        coJustRun { mockBookDao.replaceBooks(any()) }

        // Act: Call refreshBooks
        repository.refreshBooks(2025, 2025)

        // Assert: Verify network data source and DAO interactions
        coVerify { mockNetworkDataSource.fetchBooks("first_publish_year:[2025 TO 2025]") }
        coVerify { mockBookDao.replaceBooks(match { it.size == 2 }) }
    }

    @Test
    fun `getBookById returns the correct book`() = runTest {
        // Arrange: Mock DAO to return a specific book
        val mockLocalBook = getMockLocalBook()
        coEvery { mockBookDao.getBookById(mockLocalBook.id) } returns mockLocalBook

        // Act: Call getBookById
        val book = repository.getBookById(mockLocalBook.id)

        // Assert: Verify the correct book is returned
        assertThat(book).isNotNull()
        assertThat(book).isEqualTo(getMockBook1())
    }

    @Test
    fun `addToWishlist adds book to wishlist`() = runTest {
        // Act: Call addToWishlist
        repository.addToWishlist(1)

        // Assert: Verify DAO interaction
        coVerify { mockBookDao.addToWishlist(WishlistEntity(bookId = 1)) }
    }

    @Test
    fun `removeFromWishlist removes book from wishlist`() = runTest {
        // Act: Call removeFromWishlist
        repository.removeFromWishlist(1)

        // Assert: Verify DAO interaction
        coVerify { mockBookDao.removeFromWishlist(1) }
    }

    @Test
    fun `isWishlisted checks if book is in wishlist`() = runTest {
        // Arrange: Mock DAO to return true
        coEvery { mockBookDao.isWishlisted(1) } returns true

        // Act: Call isWishlisted
        val result = repository.isWishlisted(1)

        // Assert: Verify the correct result is returned
        assertThat(result).isTrue()
    }
}