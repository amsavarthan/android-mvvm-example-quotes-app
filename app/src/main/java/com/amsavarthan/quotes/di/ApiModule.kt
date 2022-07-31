package com.amsavarthan.quotes.di

import com.amsavarthan.quotes.data.api.QuotesApiService
import com.amsavarthan.quotes.helpers.Constants.BASE_URL
import com.amsavarthan.quotes.repository.QuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesRetrofitClient(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providesQuotesApiService(retrofit: Retrofit): QuotesApiService {
        return retrofit.create(QuotesApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesQuotesRepository(quotesApiService: QuotesApiService): QuotesRepository {
        return QuotesRepository(quotesApiService)
    }

}