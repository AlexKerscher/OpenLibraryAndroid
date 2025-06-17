package dev.kerscher.openlibraryandroid.data.source.network

import com.google.gson.annotations.SerializedName
import dev.kerscher.openlibraryandroid.data.Book


data class BookResponse (
    @SerializedName("numFound"      ) var numFound      : Int?            = null,
    @SerializedName("start"         ) var start         : Int?            = null,
    @SerializedName("numFoundExact" ) var numFoundExact : Boolean?        = null,
    @SerializedName("docs"          ) var books         : ArrayList<NetworkBook> = arrayListOf(),
    @SerializedName("q"             ) var q             : String?         = null,
    @SerializedName("offset"        ) var offset        : String?         = null
)

// Extension function to map the BookResponse to a list of domain books
fun BookResponse.toDomainModel(): List<Book> {
    return books.map { it.toDomainModel() }
}