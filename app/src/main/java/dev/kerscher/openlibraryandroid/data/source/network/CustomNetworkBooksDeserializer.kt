package dev.kerscher.openlibraryandroid.data.source.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CustomNetworkBooksDeserializer : JsonDeserializer<BookResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): BookResponse {
        val jsonObject = json.asJsonObject

        // Read one instance of `numFound`
        val numFound = jsonObject["numFound"].asInt

        // Read other fields normally
        val start = jsonObject["start"].asInt
        val numFoundExact = jsonObject["numFoundExact"].asBoolean
        val q = jsonObject["q"].asString
        val offset =  if (jsonObject["offset"].isJsonNull) null else jsonObject["offset"].asString
        val docs = context.deserialize<ArrayList<NetworkBook>>(jsonObject["docs"], object : TypeToken<ArrayList<NetworkBook>>() {}.type)

        return BookResponse(numFound, start, numFoundExact, docs, q, offset)
    }
}