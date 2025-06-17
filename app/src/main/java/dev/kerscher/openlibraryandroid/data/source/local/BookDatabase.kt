package dev.kerscher.openlibraryandroid.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [LocalBook::class, WishlistEntity::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}