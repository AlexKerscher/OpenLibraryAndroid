package dev.kerscher.openlibraryandroid.data

import dev.kerscher.openlibraryandroid.data.source.local.BookDao
import dev.kerscher.openlibraryandroid.data.source.local.WishlistEntity
import dev.kerscher.openlibraryandroid.data.source.local.toDomain
import dev.kerscher.openlibraryandroid.data.source.local.toLocal
import dev.kerscher.openlibraryandroid.data.source.network.BookNetworkDataSource
import dev.kerscher.openlibraryandroid.data.source.network.toDomainModel
import dev.kerscher.openlibraryandroid.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultBookRepository @Inject constructor(
    private val networkDataSource: BookNetworkDataSource,
    private val bookDao: BookDao, // Room DAO for local storage
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : dev.kerscher.openlibraryandroid.data.BookRepository {

    // Expose a stream of books from the database
    override fun getBooksStream(): Flow<List<Book>> {
        return bookDao.getAllBooks().map { localBooks ->
            withContext(dispatcher) { localBooks.map { it.toDomain() } }
        }
    }

    override suspend fun getBooks(startYear: Int, endYear: Int, forceUpdate: Boolean): List<Book> {
        if (forceUpdate) {
            refreshBooks(startYear, endYear)
        }
        return bookDao.getAllBooksSync().map { it.toDomain() }
    }

    override suspend fun getBookById(bookId: Int): Book? {
        return bookDao.getBookById(bookId)?.toDomain()
    }

    override suspend fun refreshBooks(startYear: Int, endYear: Int) {
        withContext(dispatcher) {
            val query = "first_publish_year:[$startYear TO $endYear]"
            val response = networkDataSource.fetchBooks(query)
            val domainBooks = response.toDomainModel()
            bookDao.replaceBooks(domainBooks.map { it.toLocal() })
        }
    }

    // Wishlist operations
    suspend fun isWishlisted(bookId: Int): Boolean {
        return bookDao.isWishlisted(bookId)
    }

    suspend fun addToWishlist(bookId: Int) {
        bookDao.addToWishlist(WishlistEntity(bookId))
    }

    suspend fun removeFromWishlist(bookId: Int) {
        bookDao.removeFromWishlist(bookId)
    }
}
