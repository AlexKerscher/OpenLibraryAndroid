package dev.kerscher.openlibraryandroid.data.source.network

import retrofit2.http.GET
import retrofit2.http.Query

// Define API endpoints
interface ApiService {
    @GET("search.json")
    suspend fun getLatestBooks(
        @Query("q") query: String = "first_publish_year:[2024 TO 2025]",
        @Query("sort") sort: String = "new"
    ): BookResponse
}