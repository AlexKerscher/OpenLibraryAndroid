package dev.kerscher.openlibraryandroid

import androidx.navigation.NavHostController
import dev.kerscher.openlibraryandroid.TestAppDestinationsArgs.BOOK_ID_ARG

private object TestAppScreens {
    const val BOOKS_SCREEN = "books"
    const val BOOK_DETAIL_SCREEN = "book"
}

object TestAppDestinationsArgs {
    const val BOOK_ID_ARG = "bookId"
}

object TestAppDestinations {
    const val BOOKS_ROUTE = TestAppScreens.BOOKS_SCREEN
    const val BOOK_DETAIL_ROUTE = "${TestAppScreens.BOOK_DETAIL_SCREEN}/{$BOOK_ID_ARG}"
}

/**
 * Models the navigation actions in the app.
 */
class TestAppNavigationActions(private val navController: NavHostController) {

    fun navigateToBookDetail(bookId: Int) {
        navController.navigate("${TestAppScreens.BOOK_DETAIL_SCREEN}/$bookId")
    }
}