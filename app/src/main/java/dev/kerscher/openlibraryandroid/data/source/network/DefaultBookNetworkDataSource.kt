package dev.kerscher.openlibraryandroid.data.source.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultBookNetworkDataSource @Inject constructor(
    private val apiService: ApiService
) : BookNetworkDataSource {

    override suspend fun fetchBooks(query: String): BookResponse {
        return apiService.getLatestBooks(query)
    }
}
