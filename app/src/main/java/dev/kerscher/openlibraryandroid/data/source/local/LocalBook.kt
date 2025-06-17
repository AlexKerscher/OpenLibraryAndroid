package dev.kerscher.openlibraryandroid.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.kerscher.openlibraryandroid.data.Book

@Entity(tableName = "books")
data class LocalBook(
    @PrimaryKey val id: Int,
    val title: String,
    val authors: String, // Store authors as a comma-separated string
    val publishYear: Int,
    val isbn: String?, // Store a single ISBN for simplicity
    val publisher: String?, // First publisher in the list
    val publishDate: String?, // First publish date in the list
    val numberOfPagesMedian: Int? // Median number of pages
)

// Mapping from Domain Model (Book) to LocalBook
fun Book.toLocal(): LocalBook {
    return LocalBook(
        id = id,
        title = title,
        authors = authors.joinToString(","), // Convert list to comma-separated string
        publishYear = publishYear,
        isbn = isbn,
        publisher = publisher,
        publishDate = publishDate,
        numberOfPagesMedian = numberOfPagesMedian
    )
}

// Mapping from LocalBook to Domain Model
fun LocalBook.toDomain(): Book {
    return Book(
        id = id,
        title = title,
        authors = authors.split(","), // Convert comma-separated string back to a list
        publishYear = publishYear,
        isbn = isbn,
        publisher = publisher,
        publishDate = publishDate,
        numberOfPagesMedian = numberOfPagesMedian
    )
}
