package dev.kerscher.openlibraryandroid.data.source.network

import com.google.gson.annotations.SerializedName
import dev.kerscher.openlibraryandroid.data.Book

data class NetworkBook(
    @SerializedName("author_alternative_name" ) var authorAlternativeName : ArrayList<String> = arrayListOf(),
    @SerializedName("author_key"              ) var authorKey             : ArrayList<String> = arrayListOf(),
    @SerializedName("author_name"             ) var authorName            : ArrayList<String> = arrayListOf(),
    @SerializedName("cover_edition_key"       ) var coverEditionKey       : String?           = null,
    @SerializedName("cover_i"                 ) var coverI                : Int?              = null,
    @SerializedName("ebook_access"            ) var ebookAccess           : String?           = null,
    @SerializedName("ebook_count_i"           ) var ebookCountI           : Int?              = null,
    @SerializedName("edition_count"           ) var editionCount          : Int?              = null,
    @SerializedName("edition_key"             ) var editionKey            : ArrayList<String> = arrayListOf(),
    @SerializedName("first_publish_year"      ) var firstPublishYear      : Int?              = null,
    @SerializedName("has_fulltext"            ) var hasFulltext           : Boolean?          = null,
    @SerializedName("ia"                      ) var ia                    : ArrayList<String> = arrayListOf(),
    @SerializedName("ia_collection"           ) var iaCollection          : ArrayList<String> = arrayListOf(),
    @SerializedName("ia_collection_s"         ) var iaCollectionS         : String?           = null,
    @SerializedName("isbn"                    ) var isbn                  : ArrayList<String> = arrayListOf(),
    @SerializedName("key"                     ) var key                   : String?           = null,
    @SerializedName("language"                ) var language              : ArrayList<String> = arrayListOf(),
    @SerializedName("last_modified_i"         ) var lastModifiedI         : Int?              = null,
    @SerializedName("number_of_pages_median"  ) var numberOfPagesMedian   : Int?              = null,
    @SerializedName("printdisabled_s"         ) var printdisabledS        : String?           = null,
    @SerializedName("public_scan_b"           ) var publicScanB           : Boolean?          = null,
    @SerializedName("publish_date"            ) var publishDate           : ArrayList<String> = arrayListOf(),
    @SerializedName("publish_year"            ) var publishYear           : ArrayList<Int>    = arrayListOf(),
    @SerializedName("publisher"               ) var publisher             : ArrayList<String> = arrayListOf(),
    @SerializedName("publisher_facet"         ) var publisherFacet        : ArrayList<String> = arrayListOf(),
    @SerializedName("seed"                    ) var seed                  : ArrayList<String> = arrayListOf(),
    @SerializedName("title"                   ) var title                 : String?           = null,
    @SerializedName("title_suggest"           ) var titleSuggest          : String?           = null,
    @SerializedName("title_sort"              ) var titleSort             : String?           = null,
    @SerializedName("type"                    ) var type                  : String?           = null,
    @SerializedName("_version_"               ) var Version_              : Int?              = null,
    @SerializedName("author_facet"            ) var authorFacet           : ArrayList<String> = arrayListOf()
)

fun NetworkBook.toDomainModel(): Book {
    return Book(
        id = key?.hashCode() ?: 0,
        title = title.orEmpty(),
        authors = authorName ?: emptyList(),
        publishYear = firstPublishYear ?: 0,
        isbn = isbn.firstOrNull(), // Use the first available ISBN
        publisher = publisher.firstOrNull(), // Use the first available publisher
        publishDate = publishDate.firstOrNull(), // Use the first publish date
        numberOfPagesMedian = numberOfPagesMedian)
}