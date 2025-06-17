package dev.kerscher.openlibraryandroid.ui.bookdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kerscher.openlibraryandroid.TestAppDestinationsArgs
import dev.kerscher.openlibraryandroid.data.Book
import dev.kerscher.openlibraryandroid.data.DefaultBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: DefaultBookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookId: Int = savedStateHandle[TestAppDestinationsArgs.BOOK_ID_ARG] ?: -1

    // Mutable StateFlows to manage the UI state
    private val _isWishlisted = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(true)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _bookDetails = MutableStateFlow<Book?>(null)

    val uiState: StateFlow<BookDetailUiState> = combine(
        _isWishlisted, _isLoading, _errorMessage, _bookDetails
    ) { isWishlisted, isLoading, errorMessage, bookDetails ->
        BookDetailUiState(
            book = bookDetails,
            isWishlisted = isWishlisted,
            isLoading = isLoading,
            errorMessage = errorMessage
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), BookDetailUiState())

    init {
        fetchBookDetails()
    }

    private fun fetchBookDetails() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch book details by ID
                val book = bookRepository.getBookById(bookId)
                _bookDetails.value = book
                // Check if the book is wishlisted
                _isWishlisted.value = bookRepository.isWishlisted(bookId)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load book details."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            if (_isWishlisted.value) {
                bookRepository.removeFromWishlist(bookId)
                _isWishlisted.value = false
            } else {
                bookRepository.addToWishlist(bookId)
                _isWishlisted.value = true
            }
        }
    }
}

data class BookDetailUiState(
    val book: Book? = null,
    val isWishlisted: Boolean = false,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
