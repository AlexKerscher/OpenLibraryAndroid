package dev.kerscher.openlibraryandroid.data

import kotlinx.coroutines.flow.Flow


interface BookRepository {
    // Stream of books, useful for UI state management (e.g., Live Updates)
    fun getBooksStream(): Flow<List<dev.kerscher.openlibraryandroid.data.Book>>

    // One-shot fetch of books from remote source
    suspend fun getBooks(startYear: Int, endYear: Int, forceUpdate: Boolean = false): List<dev.kerscher.openlibraryandroid.data.Book>

    // Get books by ID
    suspend fun getBookById(bookId: Int): dev.kerscher.openlibraryandroid.data.Book?

    // Force refresh books (e.g., fetch from remote and update local cache)
    suspend fun refreshBooks(startYear: Int, endYear: Int)
}