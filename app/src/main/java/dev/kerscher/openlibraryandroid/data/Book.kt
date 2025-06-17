package dev.kerscher.openlibraryandroid.data

data class Book(
    val id: Int,
    val title: String,
    val authors: List<String>,
    val publishYear: Int,
    val isbn: String?, // Nullable because some books lack an ISBN
    val publisher: String?,
    val publishDate: String?,
    val numberOfPagesMedian: Int?
)

fun Book.getCoverURL() : String?{
    isbn?.let { return "https://covers.openlibrary.org/b/isbn/$isbn-L.jpg?default=false" }
    return null
}

fun Book.getCoverThumbnail() : String?{
    isbn?.let { return "https://covers.openlibrary.org/b/isbn/$isbn-M.jpg?default=false" }
    return null
}