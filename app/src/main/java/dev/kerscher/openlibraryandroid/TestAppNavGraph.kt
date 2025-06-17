package dev.kerscher.openlibraryandroid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.kerscher.openlibraryandroid.ui.bookdetails.BookDetailScreen
import dev.kerscher.openlibraryandroid.ui.bookslist.BooksListScreen

@Composable
fun TestAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = TestAppDestinations.BOOKS_ROUTE,
    navActions: TestAppNavigationActions = remember(navController) {
        TestAppNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Books list screen
        composable(TestAppDestinations.BOOKS_ROUTE) {
            BooksListScreen(
                onBookSelected = { bookId ->
                    navActions.navigateToBookDetail(bookId)
                }
            )
        }
        // Book detail screen
        composable(
            TestAppDestinations.BOOK_DETAIL_ROUTE,
            arguments = listOf(navArgument(TestAppDestinationsArgs.BOOK_ID_ARG) {
                type = NavType.IntType
            })
        ) { entry ->
            BookDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}