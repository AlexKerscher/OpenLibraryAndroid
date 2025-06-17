@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.kerscher.openlibraryandroid

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import dev.kerscher.openlibraryandroid.ui.bookslist.BookListViewModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookListViewModelUnitTests {

    // Mocks
    private val repository: dev.kerscher.openlibraryandroid.data.BookRepository = mockk(relaxed = true)
    private val savedStateHandle = SavedStateHandle(
        mapOf(
            "isLoading" to false,
            "isSwipeRefreshing" to false
        )
    )
    private lateinit var viewModel: BookListViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = BookListViewModel(repository, savedStateHandle)
    }

    @Test
    fun `uiState emits correct initial values`() = runTest {
        // Arrange: Mock repository to emit an empty list
        every { repository.getBooksStream() } returns flowOf(emptyList())

        // Act: Collect uiState
        viewModel.uiState.test {
            val initialState = awaitItem()

            // Assert: Check initial state
            assertTrue(initialState.books.isEmpty())
            assertFalse(initialState.isLoading)
            assertFalse(initialState.isSwipeToRefreshing)
            assertNull(initialState.userMessage)
        }
    }

//TODO fix those tests

//    @Test
//    fun `refreshBooks sets isLoading and stops when completed`() = runTest {
//        // Arrange: Mock repository refresh behavior
//        coJustRun { repository.refreshBooks(any(), any()) }
//
//        // Act: Trigger refreshBooks
//        viewModel.refreshBooks(2025, 2025, isSwipingToRefresh = false)
//
//        // Assert: Collect uiState and verify state transitions
//        viewModel.uiState.test {
//            // Initial state should have isLoading = false
//            val initialState = awaitItem()
//            assertFalse(initialState.isLoading)
//
//            // Verify isLoading = true after starting refresh
//            val loadingState = awaitItem()
//            assertTrue(loadingState.isLoading)
//
//            // Verify isLoading = false after refresh completes
//            val finalState = awaitItem()
//            assertFalse(finalState.isLoading)
//
//            // Ensure no more emissions
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `refreshBooks sets isSwipeToRefreshing and stops when completed`() = runTest {
//        // Arrange: Mock repository refresh behavior
//        coJustRun { repository.refreshBooks(any(), any()) }
//
//        // Act: Trigger refreshBooks with swipe-to-refresh
//        viewModel.refreshBooks(2025, 2025, isSwipingToRefresh = true)
//
//        // Assert: Collect uiState and verify state transitions
//        viewModel.uiState.test {
//            val initialState = awaitItem()
//            assertFalse(initialState.isSwipeToRefreshing)
//
//            val refreshingState = awaitItem()
//            assertTrue(refreshingState.isSwipeToRefreshing)
//
//            val finalState = awaitItem()
//            assertFalse(finalState.isSwipeToRefreshing)
//
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `refreshBooks updates uiState with books from repository`() = runTest {
//        // Arrange: Mock repository to return some books
//        val mockBooks = listOf(getMockBook1())
//        every { repository.getBooksStream() } returns flowOf(mockBooks)
//
//        // Act: Trigger refreshBooks
//        viewModel.refreshBooks(2025, 2025, isSwipingToRefresh = false)
//        runCurrent() // Ensure all pending coroutines are processed
//
//        // Assert: Collect uiState and verify books
//        viewModel.uiState.test {
//            val initialState = awaitItem()
//            assertTrue(initialState.books.isEmpty()) // Initial state
//
//            val updatedState = awaitItem()
//            assertEquals(mockBooks, updatedState.books)
//
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `refreshBooks sets userMessage on error`() = runTest {
//        // Arrange: Mock repository to throw an exception
//        coEvery { repository.refreshBooks(any(), any()) } throws Exception("Network error")
//
//        // Act: Trigger refreshBooks
//        viewModel.refreshBooks(2025, 2025, isSwipingToRefresh = false)
//
//        // Assert: Collect uiState and verify error state
//        viewModel.uiState.test {
//            val initialState = awaitItem()
//            assertNull(initialState.userMessage)
//
//            val errorState = awaitItem()
//            assertEquals(R.string.loading_books_error, errorState.userMessage)
//
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `showSnackbarMessage updates userMessage`() {
//        // Act: Show a snackbar message
//        viewModel.showSnackbarMessage(R.string.loading_books_error)
//
//        // Assert: Verify the userMessage in uiState
//        assertEquals(R.string.loading_books_error, viewModel.uiState.value.userMessage)
//    }
//
//    @Test
//    fun `snackbarMessageShown clears userMessage`() {
//        // Arrange: Set a snackbar message
//        viewModel.showSnackbarMessage(R.string.loading_books_error)
//
//        // Act: Clear the snackbar message
//        viewModel.snackbarMessageShown()
//
//        // Assert: Verify the userMessage is cleared
//        assertNull(viewModel.uiState.value.userMessage)
//    }
}
