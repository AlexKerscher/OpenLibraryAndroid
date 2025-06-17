package dev.kerscher.openlibraryandroid

import com.google.common.truth.Truth.assertThat
import dev.kerscher.openlibraryandroid.data.getCoverThumbnail
import dev.kerscher.openlibraryandroid.data.getCoverURL
import dev.kerscher.openlibraryandroid.data.source.local.toDomain
import dev.kerscher.openlibraryandroid.data.source.local.toLocal
import dev.kerscher.openlibraryandroid.data.source.network.toDomainModel
import dev.kerscher.openlibraryandroid.util.getMockBook1
import dev.kerscher.openlibraryandroid.util.getMockBook3
import dev.kerscher.openlibraryandroid.util.getMockLocalBook
import dev.kerscher.openlibraryandroid.util.getMockNetworkBook1
import org.junit.Test

class BookDomainModelUnitTests {

    @Test
    fun `NetworkBook toDomainModel maps correctly`() {
        // Arrange: Create a NetworkBook with sample data
        val networkBook = getMockNetworkBook1()

        // Act: Map it to a domain model
        val domainBook = networkBook.toDomainModel()

        // Assert: Verify the mapping
        assertThat(domainBook.id).isEqualTo(networkBook.key.hashCode())
        assertThat(domainBook.title).isEqualTo("Never Flinch")
        assertThat(domainBook.authors).containsExactly("Stephen King")
        assertThat(domainBook.publishYear).isEqualTo(2025)
        assertThat(domainBook.isbn).isEqualTo("9781668089330")
        assertThat(domainBook.publisher).isEqualTo("Simon & Schuster")
        assertThat(domainBook.publishDate).isEqualTo("2025")
        assertThat(domainBook.numberOfPagesMedian).isEqualTo(448)
    }

    @Test
    fun `Book toLocal maps correctly`() {
        // Arrange: Create a domain model (Book) with sample data
        val domainBook = getMockBook1()

        // Act: Map it to a local model
        val localBook = domainBook.toLocal()

        // Assert: Verify the mapping
        assertThat(localBook.id).isEqualTo(-115688095)
        assertThat(localBook.title).isEqualTo("Never Flinch")
        assertThat(localBook.authors).isEqualTo("Stephen King")
        assertThat(localBook.publishYear).isEqualTo(2025)
        assertThat(localBook.isbn).isEqualTo("9781668089330")
        assertThat(localBook.publisher).isEqualTo("Simon & Schuster")
        assertThat(localBook.publishDate).isEqualTo("2025")
        assertThat(localBook.numberOfPagesMedian).isEqualTo(448)
    }

    @Test
    fun `LocalBook toDomain maps correctly`() {
        // Arrange: Create a local model (LocalBook) with sample data
        val localBook = getMockLocalBook()

        // Act: Map it back to a domain model
        val domainBook = localBook.toDomain()

        // Assert: Verify the mapping
        assertThat(domainBook.id).isEqualTo(-115688095)
        assertThat(domainBook.title).isEqualTo("Never Flinch")
        assertThat(domainBook.authors).containsExactly("Stephen King") // Authors are split back into a list
        assertThat(domainBook.publishYear).isEqualTo(2025)
        assertThat(domainBook.isbn).isEqualTo("9781668089330")
        assertThat(domainBook.publisher).isEqualTo("Simon & Schuster")
        assertThat(domainBook.publishDate).isEqualTo("2025")
        assertThat(domainBook.numberOfPagesMedian).isEqualTo(448)
    }

    @Test
    fun `Book getCoverURL returns correct URL`() {
        // Arrange: Create a Book with an ISBN
        val book = getMockBook1()

        // Act: Get the cover URL
        val coverUrl = book.getCoverURL()

        // Assert: Verify the URL is correct
        assertThat(coverUrl).isEqualTo("https://covers.openlibrary.org/b/isbn/9781668089330-L.jpg?default=false")
    }

    @Test
    fun `Book getCoverThumbnail returns correct thumbnail URL`() {
        // Arrange: Create a Book with an ISBN
        val book = getMockBook1()

        // Act: Get the thumbnail URL
        val thumbnailUrl = book.getCoverThumbnail()

        // Assert: Verify the thumbnail URL is correct
        assertThat(thumbnailUrl).isEqualTo("https://covers.openlibrary.org/b/isbn/9781668089330-M.jpg?default=false")
    }

    @Test
    fun `Book getCoverURL returns null when ISBN is missing`() {
        // Arrange: Create a Book without an ISBN
        val book = getMockBook3()

        // Act: Get the cover URL
        val coverUrl = book.getCoverURL()

        // Assert: Verify the URL is null
        assertThat(coverUrl).isNull()
    }
}
