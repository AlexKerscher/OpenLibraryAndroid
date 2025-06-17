package dev.kerscher.openlibraryandroid

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import dev.kerscher.openlibraryandroid.data.source.network.ApiService
import dev.kerscher.openlibraryandroid.data.source.network.BookResponse
import dev.kerscher.openlibraryandroid.data.source.network.CustomNetworkBooksDeserializer
import dev.kerscher.openlibraryandroid.util.getMockJsonResponseAsString
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.HttpException

@RunWith(AndroidJUnit4::class)
class ApiServiceInstrumentedTests {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val gson = GsonBuilder()
            .registerTypeAdapter(BookResponse::class.java, CustomNetworkBooksDeserializer())
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Set the mock server's URL
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetLatestBooks_returnsBooksWhenResponseIsValid() = runBlocking {
        // Arrange: Prepare a valid mock response
        val mockResponseBody = getMockJsonResponseAsString()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockResponseBody)
        )

        // Act: Make the API call
        val response = apiService.getLatestBooks()

        // Assert: Verify the response is correct
        assertThat(response.books).hasSize(2)
        assertThat(response.books[0].title).isEqualTo("Never Flinch")
        assertThat(response.books[1].title).isEqualTo("When the Moon Hits Your Eye")
    }

    @Test
    fun testGetLatestBooks_throwsExceptionOnServerError() = runBlocking {
        // Arrange: Prepare a mock server error response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500) // Internal server error
        )

        // Act and Assert: Verify the exception is thrown
        try {
            apiService.getLatestBooks()
            fail("Expected an exception to be thrown, but none was.")
        } catch (e: HttpException) {
            assertThat(e.code()).isEqualTo(500) // Verify the error code
        }
    }
}