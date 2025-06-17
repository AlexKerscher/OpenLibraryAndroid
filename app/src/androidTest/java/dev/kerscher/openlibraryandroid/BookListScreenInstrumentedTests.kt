package dev.kerscher.openlibraryandroid

import dagger.hilt.android.testing.HiltAndroidTest

@HiltAndroidTest
class BookListScreenInstrumentedTests {
//TODO fix those tests

//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<MainActivity>()
//
//    @Before
//    fun setup() {
//        hiltRule.inject()
//    }
//
//    @Test
//    fun topBarText_showsLoading_whenIsLoading() {
//        // Arrange: Set initial state with isLoading = true
//        composeTestRule.setContent {
//            BooksListScreen(
//                onBookSelected = {},
//                viewModel = mockViewModelWithState(BookListUiState(isLoading = true))
//            )
//        }
//
//        // Assert: Check top bar text is "Loading..."
//        composeTestRule.onNodeWithText("Loading...").assertExists()
//    }
//
//    @Test
//    fun topBarText_showsNoBooksFound_whenEmpty() {
//        // Arrange: Set initial state with empty books
//        composeTestRule.setContent {
//            BooksListScreen(
//                onBookSelected = {},
//                viewModel = mockViewModelWithState(BookListUiState(books = emptyList()))
//            )
//        }
//
//        // Assert: Check top bar text is "No books found"
//        composeTestRule.onNodeWithText("No books found").assertExists()
//    }
//
//    @Test
//    fun booksList_displaysBooksCorrectly() {
//        // Arrange
//        val mockBooks = listOf(Book(id = 1, title = "Never Flinch", authors= listOf("Stephen King") , publishYear=2025, isbn="9781668089330", publisher="Simon & Schuster", publishDate="2025", numberOfPagesMedian=448), Book(id = 2, title = "When the Moon Hits Your Eye", authors= listOf("John Scalzi") , publishYear=2025, isbn="9781529082913", publisher="Tor", publishDate="2025", numberOfPagesMedian=336))
//        composeTestRule.setContent {
//            BooksListScreen(
//                onBookSelected = {},
//                viewModel = mockViewModelWithState(BookListUiState(books = mockBooks))
//            )
//        }
//
//        // Assert: Verify both books are displayed
//        composeTestRule.onNodeWithText("Never Flinch").assertExists()
//        composeTestRule.onNodeWithText("When the Moon Hits Your Eye").assertExists()
//    }
//
//    @Test
//    fun emptyState_refreshButton_works() {
//        // Arrange: Start in an empty state
//        val mockViewModel = mockViewModelWithState(BookListUiState(books = emptyList()))
//        composeTestRule.setContent {
//            BooksListScreen(
//                onBookSelected = {},
//                viewModel = mockViewModel
//            )
//        }
//
//        // Act: Click the refresh button
//        composeTestRule.onNodeWithText("Refresh").performClick()
//
//        // Assert: Verify refreshBooks was called
//        coVerify { mockViewModel.refreshBooks(any(), any(), isSwipingToRefresh = false) }
//    }
//
//    // Helper to mock ViewModel
//    private fun mockViewModelWithState(state: BookListUiState): BookListViewModel {
//        return mockk(relaxed = true) {
//            every { uiState } returns MutableStateFlow(state) // Provide mocked state
//            coEvery { refreshBooks(any(), any(), any()) } just Runs // Mock refreshBooks call
//        }
//    }
}