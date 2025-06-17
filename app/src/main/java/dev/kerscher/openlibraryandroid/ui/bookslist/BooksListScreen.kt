package dev.kerscher.openlibraryandroid.ui.bookslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kerscher.openlibraryandroid.R
import dev.kerscher.openlibraryandroid.data.Book
import dev.kerscher.openlibraryandroid.data.getCoverThumbnail
import dev.kerscher.openlibraryandroid.util.LoadingContent
import dev.kerscher.openlibraryandroid.util.performHapticFeedback


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksListScreen(
    onBookSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookListViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    val topBarText = when {
                        uiState.isLoading -> stringResource(R.string.loading_books)
                        uiState.books.isEmpty() -> stringResource(R.string.no_books_found)
                        else -> stringResource(R.string.books_list_title)
                    }
                    Text(topBarText)
                }
            )
        }
    ) { paddingValues ->
        BooksContent(
            isLoading = uiState.isLoading,
            isSwipeToRefreshing = uiState.isSwipeToRefreshing,
            books = uiState.books,
            onRefresh = { isSwipeToRefresh ->
                viewModel.refreshBooks(2025, 2025, isSwipingToRefresh = isSwipeToRefresh)
            },
            onBookClick = onBookSelected,
            modifier = Modifier.padding(paddingValues)
        )
        // Snackbar only pops up if there's an error
        uiState.userMessage?.let { message ->
            val snackbarText = stringResource(id = message)
            LaunchedEffect(snackbarHostState, viewModel, message, snackbarText) {
                snackbarHostState.showSnackbar(snackbarText)
                viewModel.snackbarMessageShown()
            }
        }
    }
}

@Composable
private fun BooksContent(
    isLoading: Boolean,
    isSwipeToRefreshing: Boolean,
    books: List<Book>,
    onRefresh: (isSwipeToRefresh: Boolean) -> Unit,
    onBookClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {

        LoadingContent(
            loading = isSwipeToRefreshing,
            empty = books.isEmpty() && !isLoading && !isSwipeToRefreshing,
            emptyContent = { BooksEmptyContent(onRefresh = { onRefresh(false) }) },
            onRefresh = { onRefresh(true) }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(books) { book ->
                    BookItem(book = book, onBookClick = onBookClick)
                }
            }
        }

        // Show overlay spinner ONLY during button-triggered refresh
        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)), // Semi-transparent overlay
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(dimensionResource(R.dimen.big_spinner_size))
                )
            }
        }
    }
}


@Composable
private fun BookItem(book: Book, onBookClick: (Int) -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBookClick(book.id)
                performHapticFeedback(context)
            }
            .padding(dimensionResource(R.dimen.horizontal_margin))
    ) {
        Box(
            modifier = Modifier
                .width(dimensionResource(R.dimen.book_thumbnail_width))
                .height(dimensionResource(R.dimen.book_thumbnail_height))
                .aspectRatio(2f / 3f)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.LightGray)
        ) {
            val coverUrl = book.getCoverThumbnail()

            if (coverUrl != null) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(coverUrl)
                        .crossfade(true)
                        .crossfade(50)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = stringResource(R.string.book_thumbnail_content_description),
                    loading = {
                        // Spinner as a loading indicator
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(dimensionResource(R.dimen.small_spinner_size)),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = dimensionResource(R.dimen.small_spinner_stroke_width)
                            )
                        }
                    },
                    error = {
                        NoCoverFallbackBox()
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()

                )
            } else {
                NoCoverFallbackBox()
            }
        }

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.book_list_spacer_height)))

        // Book details
        Column(modifier = Modifier.weight(1f)) {
            Text(text = book.title ?: stringResource(R.string.no_book_title_available), style = MaterialTheme.typography.titleLarge)
            Text(
                text = book.authors.firstOrNull() ?: stringResource(R.string.no_book_author_available),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun NoCoverFallbackBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_book_cover_available),
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BooksEmptyContent(onRefresh: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.horizontal_margin)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_book_24px),
            contentDescription = stringResource(R.string.no_books_image_content_description),
            modifier = Modifier
                .size(dimensionResource(R.dimen.empty_book_list_icon_size))
                .animateContentSize()
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.vertical_margin)))
        Text(
            text = stringResource(R.string.no_books_available),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.empty_list_small_spacer_height)))
        Button(
            onClick = onRefresh,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.vertical_margin))
                .animateContentSize()
        ) {
            Text(stringResource(R.string.refresh_me_placeholder))
        }
    }
}