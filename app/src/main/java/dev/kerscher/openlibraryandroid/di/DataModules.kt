package dev.kerscher.openlibraryandroid.di

import android.content.Context
import androidx.room.Room
import dev.kerscher.openlibraryandroid.data.DefaultBookRepository
import dev.kerscher.openlibraryandroid.data.source.local.BookDao
import dev.kerscher.openlibraryandroid.data.source.local.BookDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindBookRepository(repository: DefaultBookRepository): dev.kerscher.openlibraryandroid.data.BookRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BookDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BookDatabase::class.java,
            "Books.db"
        ).build()
    }

    @Provides
    fun provideBookDao(database: BookDatabase): BookDao = database.bookDao()
}
