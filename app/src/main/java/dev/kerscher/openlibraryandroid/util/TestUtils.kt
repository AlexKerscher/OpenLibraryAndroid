package dev.kerscher.openlibraryandroid.util

import dev.kerscher.openlibraryandroid.data.Book
import dev.kerscher.openlibraryandroid.data.source.local.LocalBook
import dev.kerscher.openlibraryandroid.data.source.network.BookResponse
import dev.kerscher.openlibraryandroid.data.source.network.NetworkBook

fun getMockNetworkBooks() : ArrayList<NetworkBook>{
  return arrayListOf(getMockNetworkBook1(), getMockNetworkBook2())
}

fun getMockBookResponse(): BookResponse {
    return BookResponse(
        numFound = 2,
        start = 0,
        numFoundExact = true,
        books = getMockNetworkBooks(),
        q = "first_publish_year:[2025 TO 2025]",
        offset = null
    )
}

fun getMockNetworkBook1() : NetworkBook{
    return NetworkBook(
        key = "/works/OL41942876W",
        title = "Never Flinch",
        authorName = arrayListOf("Stephen King"),
        firstPublishYear = 2025,
        isbn = arrayListOf("9781668089330", "1668089335"),
        publisher = arrayListOf("Simon & Schuster"),
        publishDate = arrayListOf("2025"),
        numberOfPagesMedian = 448
    )
}

fun getMockNetworkBook2() : NetworkBook{
    return NetworkBook(
        key = "/works/OL42230960W",
        title = "When the Moon Hits Your Eye",
        authorName = arrayListOf("John Scalzi"),
        firstPublishYear = 2025,
        isbn = arrayListOf("9781529082913", "1529082919"),
        publisher = arrayListOf("Tor"),
        publishDate = arrayListOf("2025"),
        numberOfPagesMedian = 336
    )
}

fun getMockBook1() : Book {
    return Book(
            id = -115688095,
            title = "Never Flinch",
            authors = listOf("Stephen King"),
            publishYear = 2025,
            isbn = "9781668089330",
            publisher = "Simon & Schuster",
            publishDate = "2025",
            numberOfPagesMedian = 448
        )
}

fun getMockBook3() : Book {
    return Book(
        id = -824114441,
        title = "Human Rites",
        authors = listOf("Elias Silva de Oliveira ", "Elias Empresas"),
        publishYear = 2025,
        isbn = null,
        publisher = "ELIAS EMPRESAS",
        publishDate = "2025",
        numberOfPagesMedian = null
    )
}

fun getMockLocalBook() : LocalBook {
    return LocalBook(
        id = -115688095,
        title = "Never Flinch",
        authors = "Stephen King",
        publishYear = 2025,
        isbn = "9781668089330",
        publisher = "Simon & Schuster",
        publishDate = "2025",
        numberOfPagesMedian = 448
    )
}

fun getMockJsonResponseAsString() : String {
    return "{\n" +
            "   \"numFound\":2,\n" +
            "   \"start\":0,\n" +
            "   \"numFoundExact\":true,\n" +
            "   \"docs\":[\n" +
            "      {\n" +
            "         \"author_alternative_name\":[\n" +
            "            \"KING,STEPHEN\",\n" +
            "            \"King, Stephen; Bachman, Richard\",\n" +
            "            \"Bachman, Richard (King, Stephen)\",\n" +
            "            \"King-Stephen\",\n" +
            "            \"King S.\",\n" +
            "            \"Stephen KING\",\n" +
            "            \"Ke qing xin\",\n" +
            "            \"King Stiven.\",\n" +
            "            \"KING STEPHEN\",\n" +
            "            \"\\u0643\\u064a\\u0646\\u063a\\u060c \\u0633\\u062a\\u064a\\u0641\\u0646\\u060c\",\n" +
            "            \"King Stephen\",\n" +
            "            \"King, Stephen Schriftsteller\",\n" +
            "            \"Stephen king\",\n" +
            "            \"Bachman Richard\",\n" +
            "            \"King, Stephen\",\n" +
            "            \"King, Stephen, 1947-\",\n" +
            "            \"Beryl Evans\",\n" +
            "            \"Stephen King,Stephen King\",\n" +
            "            \"king stephen\",\n" +
            "            \"S.King\",\n" +
            "            \"Bachman, Richard\",\n" +
            "            \"Stephen King:\",\n" +
            "            \"\\u0421\\u0442\\u0456\\u0432\\u0435\\u043d \\u041a\\u0456\\u043d\\u0433. \\u0421\\u0442\\u0438\\u0432\\u0435\\u043d \\u041a\\u0438\\u043d\\u0433. Stephen Edwin King\",\n" +
            "            \"Richard Bachman ( LI CHA BA HE MAN ) . Stephen King ( SI DI FEN\",\n" +
            "            \"King Stephen November 29, 2020\",\n" +
            "            \"King, Stephen.\",\n" +
            "            \"KING Stephen -\",\n" +
            "            \"King.S.\",\n" +
            "            \"Sthephen KING\",\n" +
            "            \"Srephen King\",\n" +
            "            \"Stephen King,Richard Bachman,Stephen King\",\n" +
            "            \"Bachman, Richard.\",\n" +
            "            \"Richard Bachman\",\n" +
            "            \"King, Stephen (1947- )\",\n" +
            "            \"Richard Bachman (Stephen King)\",\n" +
            "            \"STEPHEN KING\",\n" +
            "            \"Stiven King\",\n" +
            "            \"Stephen Edwin King\",\n" +
            "            \"Richard Bachman; Stephen King\",\n" +
            "            \"Stephen, King\",\n" +
            "            \"S King\",\n" +
            "            \"King. Stephen.\",\n" +
            "            \"stephen king\",\n" +
            "            \"Stiven King (pod psevdonimom Richard Bakhman)\",\n" +
            "            \"Stephen King ( Quote )\",\n" +
            "            \"King S\",\n" +
            "            \"Jin\",\n" +
            "            \"stephen-king\",\n" +
            "            \"stephen [writing as richard bachman] king\",\n" +
            "            \"King Stiven\"\n" +
            "         ],\n" +
            "         \"author_key\":[\n" +
            "            \"OL19981A\"\n" +
            "         ],\n" +
            "         \"author_name\":[\n" +
            "            \"Stephen King\"\n" +
            "         ],\n" +
            "         \"cover_edition_key\":\"OL56971290M\",\n" +
            "         \"cover_i\":14825714,\n" +
            "         \"ebook_access\":\"no_ebook\",\n" +
            "         \"ebook_count_i\":0,\n" +
            "         \"edition_count\":1,\n" +
            "         \"edition_key\":[\n" +
            "            \"OL56971290M\"\n" +
            "         ],\n" +
            "         \"first_publish_year\":2025,\n" +
            "         \"has_fulltext\":false,\n" +
            "         \"isbn\":[\n" +
            "            \"9781668089330\",\n" +
            "            \"1668089335\"\n" +
            "         ],\n" +
            "         \"key\":\"/works/OL41942876W\",\n" +
            "         \"last_modified_i\":1732396628,\n" +
            "         \"public_scan_b\":false,\n" +
            "         \"publish_date\":[\n" +
            "            \"2025\"\n" +
            "         ],\n" +
            "         \"publish_year\":[\n" +
            "            2025\n" +
            "         ],\n" +
            "         \"publisher\":[\n" +
            "            \"Simon & Schuster\"\n" +
            "         ],\n" +
            "         \"seed\":[\n" +
            "            \"/books/OL56971290M\",\n" +
            "            \"/works/OL41942876W\",\n" +
            "            \"/authors/OL19981A\"\n" +
            "         ],\n" +
            "         \"title\":\"Never Flinch\",\n" +
            "         \"title_sort\":\"Never Flinch\",\n" +
            "         \"title_suggest\":\"Never Flinch\",\n" +
            "         \"type\":\"work\",\n" +
            "         \"readinglog_count\":0,\n" +
            "         \"want_to_read_count\":0,\n" +
            "         \"currently_reading_count\":0,\n" +
            "         \"already_read_count\":0,\n" +
            "         \"number_of_pages_median\":448,\n" +
            "         \"publisher_facet\":[\n" +
            "            \"Simon & Schuster\"\n" +
            "         ],\n" +
            "         \"_version_\":1816549535639404544,\n" +
            "         \"author_facet\":[\n" +
            "            \"OL19981A Stephen King\"\n" +
            "         ]\n" +
            "      },\n" +
            "      {\n" +
            "         \"author_key\":[\n" +
            "            \"OL1394231A\"\n" +
            "         ],\n" +
            "         \"author_name\":[\n" +
            "            \"John Scalzi\"\n" +
            "         ],\n" +
            "         \"cover_edition_key\":\"OL57286480M\",\n" +
            "         \"cover_i\":14829868,\n" +
            "         \"ebook_access\":\"no_ebook\",\n" +
            "         \"ebook_count_i\":0,\n" +
            "         \"edition_count\":1,\n" +
            "         \"edition_key\":[\n" +
            "            \"OL57286480M\"\n" +
            "         ],\n" +
            "         \"first_publish_year\":2025,\n" +
            "         \"format\":[\n" +
            "            \"eBook\"\n" +
            "         ],\n" +
            "         \"has_fulltext\":false,\n" +
            "         \"isbn\":[\n" +
            "            \"9781529082913\",\n" +
            "            \"1529082919\"\n" +
            "         ],\n" +
            "         \"key\":\"/works/OL42230960W\",\n" +
            "         \"last_modified_i\":1734387193,\n" +
            "         \"public_scan_b\":false,\n" +
            "         \"publish_date\":[\n" +
            "            \"2025\"\n" +
            "         ],\n" +
            "         \"publish_year\":[\n" +
            "            2025\n" +
            "         ],\n" +
            "         \"publisher\":[\n" +
            "            \"Tor\"\n" +
            "         ],\n" +
            "         \"seed\":[\n" +
            "            \"/books/OL57286480M\",\n" +
            "            \"/works/OL42230960W\",\n" +
            "            \"/authors/OL1394231A\"\n" +
            "         ],\n" +
            "         \"title\":\"When the Moon Hits Your Eye\",\n" +
            "         \"title_sort\":\"When the Moon Hits Your Eye\",\n" +
            "         \"title_suggest\":\"When the Moon Hits Your Eye\",\n" +
            "         \"type\":\"work\",\n" +
            "         \"readinglog_count\":0,\n" +
            "         \"want_to_read_count\":0,\n" +
            "         \"currently_reading_count\":0,\n" +
            "         \"already_read_count\":0,\n" +
            "         \"publisher_facet\":[\n" +
            "            \"Tor\"\n" +
            "         ],\n" +
            "         \"_version_\":1818636789824356352,\n" +
            "         \"author_facet\":[\n" +
            "            \"OL1394231A John Scalzi\"\n" +
            "         ]\n" +
            "      }\n" +
            "   ],\n" +
            "   \"num_found\":2,\n" +
            "   \"q\":\"first_publish_year:[2025 TO 2025]\",\n" +
            "   \"offset\":null\n" +
            "}"
}