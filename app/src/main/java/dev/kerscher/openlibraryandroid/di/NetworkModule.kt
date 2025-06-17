package dev.kerscher.openlibraryandroid.di

import com.google.gson.GsonBuilder
import dev.kerscher.openlibraryandroid.data.source.network.ApiService
import dev.kerscher.openlibraryandroid.data.source.network.BookNetworkDataSource
import dev.kerscher.openlibraryandroid.data.source.network.BookResponse
import dev.kerscher.openlibraryandroid.data.source.network.CustomNetworkBooksDeserializer
import dev.kerscher.openlibraryandroid.data.source.network.DefaultBookNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(BookResponse::class.java, CustomNetworkBooksDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl("https://openlibrary.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BookNetworkModule {

    @Binds
    @Singleton
    abstract fun bindBookNetworkDataSource(
        defaultBookNetworkDataSource: DefaultBookNetworkDataSource
    ): BookNetworkDataSource
}