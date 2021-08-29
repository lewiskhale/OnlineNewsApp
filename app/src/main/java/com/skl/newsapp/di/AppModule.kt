package com.skl.newsapp.di

import android.content.Context
import androidx.room.Room
import com.skl.newsapp.data.repository.local.ArticleDatabase
import com.skl.newsapp.data.repository.network.NewsApi
import com.skl.newsapp.utils.Constants.BASE_URL
import com.skl.newsapp.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNewsApi(): NewsApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(
                context,
                ArticleDatabase::class.java,
                DATABASE_NAME
            )
//            .addTypeConverter()
            .fallbackToDestructiveMigration()
            .build()
}