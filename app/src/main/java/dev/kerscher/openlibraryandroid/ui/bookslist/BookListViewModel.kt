package dev.kerscher.openlibraryandroid.ui.bookslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kerscher.openlibraryandroid.R
import dev.kerscher.openlibraryandroid.data.Book
import dev.kerscher.openlibraryandroid.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI State for the Books list screen.
 */
data class BookListUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val isSwipeToRefreshing : Boolean = false,
    val userMessage: Int? = null
)


@HiltViewModel
class BookListViewModel @Inject constructor(
    private val repository: dev.kerscher.openlibraryandroid.data.BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _userMessage = MutableStateFlow<Int?>(null)
    private val _isLoading = MutableStateFlow(savedStateHandle["isLoading"] ?: false)
    private val _isSwipeRefreshing = MutableStateFlow(savedStateHandle["isSwipeRefreshing"] ?: false)


    val uiState: StateFlow<BookListUiState> = combine(
        repository.getBooksStream(),
        _isLoading,
        _isSwipeRefreshing,
        _userMessage
    ) { books, isLoading, isSwipeRefreshing, userMessage ->
        BookListUiState(books = books, isLoading = isLoading, isSwipeToRefreshing= isSwipeRefreshing, userMessage = userMessage)
    }.stateIn(viewModelScope, WhileUiSubscribed, BookListUiState())

    fun refreshBooks(startYear: Int, endYear: Int, isSwipingToRefresh : Boolean) {
        viewModelScope.launch {
            if (isSwipingToRefresh){
                _isSwipeRefreshing.value = true
                savedStateHandle["isSwipeRefreshing"] = true
            }else {
                _isLoading.value = true
                savedStateHandle["isLoading"] = true
            }

            try {
                repository.refreshBooks(startYear, endYear)
            } catch (e: Exception) {
                showSnackbarMessage(R.string.loading_books_error)
            } finally {
                if (isSwipingToRefresh){
                    _isSwipeRefreshing.value = false
                    savedStateHandle["isSwipeRefreshing"] = false
                }else {
                    _isLoading.value = false
                    savedStateHandle["isLoading"] = false
                }
            }
        }
    }

    fun showSnackbarMessage(message: Int) {
        _userMessage.value = message
    }

    fun snackbarMessageShown() {
        _userMessage.value = null
    }
}