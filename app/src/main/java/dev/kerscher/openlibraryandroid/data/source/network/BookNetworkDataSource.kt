package dev.kerscher.openlibraryandroid.data.source.network

interface BookNetworkDataSource {

    /**
     * Fetches a list of books based on the given query parameters.
     */
    suspend fun fetchBooks(query: String): BookResponse
}
