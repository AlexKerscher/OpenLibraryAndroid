package dev.kerscher.openlibraryandroid.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class WishlistEntity(
    @PrimaryKey val bookId: Int // The unique ID of the book
)
