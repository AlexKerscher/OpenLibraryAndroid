package dev.kerscher.openlibraryandroid

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.kerscher.openlibraryandroid.data.source.local.BookDao
import dev.kerscher.openlibraryandroid.data.source.local.BookDatabase
import dev.kerscher.openlibraryandroid.data.source.local.WishlistEntity
import dev.kerscher.openlibraryandroid.util.getMockLocalBook
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class BookDaoInstrumentedTests {

    private lateinit var database: BookDatabase
    private lateinit var bookDao: BookDao

    @Before
    fun setup() {
        // Create an in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookDatabase::class.java
        ).allowMainThreadQueries().build()

        bookDao = database.bookDao()
    }

    @After
    fun teardown() {
        // Close the database after tests
        database.close()
    }

    @Test
    fun insertAndFetchBooks() = runBlocking {
        // Arrange: Insert a list of mock books
        val localBooks = listOf(getMockLocalBook())
        bookDao.insertBooks(localBooks)

        // Act: Fetch all books
        val fetchedBooks = bookDao.getAllBooksSync()

        // Assert: Verify the books are inserted and fetched correctly
        assertThat(fetchedBooks).hasSize(1)
        assertThat(fetchedBooks[0]).isEqualTo(localBooks[0])
    }

    @Test
    fun fetchBookById() = runBlocking {
        // Arrange: Insert a mock book
        val localBook = getMockLocalBook()
        bookDao.insertBooks(listOf(localBook))

        // Act: Fetch the book by its ID
        val fetchedBook = bookDao.getBookById(localBook.id)

        // Assert: Verify the book is fetched correctly
        assertThat(fetchedBook).isNotNull()
        assertThat(fetchedBook).isEqualTo(localBook)
    }

    @Test
    fun replaceBooks_clearsOldBooks() = runBlocking {
        // Arrange: Insert initial books
        val oldBooks = listOf(getMockLocalBook())
        bookDao.insertBooks(oldBooks)

        // Act: Replace with new books
        val newBooks = listOf(getMockLocalBook().copy(id = 2, title = "New Book"))
        bookDao.replaceBooks(newBooks)

        // Assert: Verify the old books are replaced
        val fetchedBooks = bookDao.getAllBooksSync()
        assertThat(fetchedBooks).hasSize(1)
        assertThat(fetchedBooks[0]).isEqualTo(newBooks[0])
    }

    @Test
    fun addToWishlist_and_checkWishlist() = runBlocking {
        // Arrange: Create a wishlist entity
        val wishlistEntity = WishlistEntity(bookId = getMockLocalBook().id)

        // Act: Add the book to the wishlist
        bookDao.addToWishlist(wishlistEntity)

        // Assert: Verify the book is in the wishlist
        val isWishlisted = bookDao.isWishlisted(wishlistEntity.bookId)
        assertThat(isWishlisted).isTrue()
    }

    @Test
    fun removeFromWishlist() = runBlocking {
        // Arrange: Add a book to the wishlist
        val wishlistEntity = WishlistEntity(bookId = getMockLocalBook().id)
        bookDao.addToWishlist(wishlistEntity)

        // Act: Remove the book from the wishlist
        bookDao.removeFromWishlist(wishlistEntity.bookId)

        // Assert: Verify the book is no longer in the wishlist
        val isWishlisted = bookDao.isWishlisted(wishlistEntity.bookId)
        assertThat(isWishlisted).isFalse()
    }

    @Test
    fun deleteAllBooks() = runBlocking {
        // Arrange: Insert books
        val books = listOf(getMockLocalBook())
        bookDao.insertBooks(books)

        // Act: Delete all books
        bookDao.deleteAllBooks()

        // Assert: Verify the books table is empty
        val fetchedBooks = bookDao.getAllBooksSync()
        assertThat(fetchedBooks).isEmpty()
    }
}