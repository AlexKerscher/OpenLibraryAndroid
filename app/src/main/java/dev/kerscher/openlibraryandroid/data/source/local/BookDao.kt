package dev.kerscher.openlibraryandroid.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<LocalBook>>

    @Query("SELECT * FROM books")
    suspend fun getAllBooksSync(): List<LocalBook>

    // Fetch a book by its ID
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): LocalBook?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<LocalBook>)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()

    @Transaction
    suspend fun replaceBooks(books: List<LocalBook>) {
        deleteAllBooks()
        insertBooks(books)
    }

    // Wishlist operations
    @Query("SELECT EXISTS(SELECT 1 FROM wishlist WHERE bookId = :bookId)")
    suspend fun isWishlisted(bookId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWishlist(wishlistEntity: WishlistEntity)

    @Query("DELETE FROM wishlist WHERE bookId = :bookId")
    suspend fun removeFromWishlist(bookId: Int)
}