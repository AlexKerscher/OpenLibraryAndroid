package dev.kerscher.openlibraryandroid.ui.bookdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kerscher.openlibraryandroid.R
import dev.kerscher.openlibraryandroid.data.Book
import dev.kerscher.openlibraryandroid.data.getCoverURL
import dev.kerscher.openlibraryandroid.util.performHapticFeedback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    onBack: () -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.book_details_page_header)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(dimensionResource(R.dimen.big_spinner_size))
                )
            }
        } else if (uiState.errorMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.errorMessage ?: stringResource(R.string.unknown_error),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            uiState.book?.let { book ->
                BookDetailsContent(
                    book = book,
                    isWishlisted = uiState.isWishlisted,
                    onWishlistToggle = { viewModel.toggleWishlist() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun BookDetailsContent(
    book: Book,
    isWishlisted: Boolean,
    onWishlistToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.horizontal_margin))
    ) {
        // Book Cover
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.LightGray)
        ) {
            val coverUrl = book.getCoverURL()

            if (coverUrl != null) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(coverUrl)
                        .crossfade(true)
                        .crossfade(500)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = stringResource(R.string.book_cover_content_description),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
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

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.vertical_margin)))

        // Book Details
        Text(text = book.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.vertical_margin)))
        Text(
            text = stringResource(R.string.authors_prefix, book.authors.joinToString()),
            style = MaterialTheme.typography.bodyMedium
        )
        book.isbn?.let { isbn ->
            Text(
                text = stringResource(R.string.isbn_prefix, isbn),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        book.publisher?.let { publisher ->
            Text(
                text = stringResource(R.string.publisher_prefix, publisher),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        book.publishDate?.let { publishDate ->
            Text(
                text = stringResource(R.string.publish_date_prefix, publishDate),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        book.numberOfPagesMedian?.let { pages ->
            Text(
                text = stringResource(R.string.pages_prefix, pages),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.book_list_spacer_height)))

        // Wishlist Button
        Button(
            onClick = {
                performHapticFeedback(context) // Trigger haptic feedback
                onWishlistToggle()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isWishlisted) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onSecondary.takeIf { isWishlisted }
                    ?: MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isWishlisted) {
                    stringResource(R.string.remove_from_wishlist)
                } else {
                    stringResource(R.string.add_to_wishlist)
                },
                color = MaterialTheme.colorScheme.onPrimary // Use appropriate text color
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
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray
        )
    }
}
