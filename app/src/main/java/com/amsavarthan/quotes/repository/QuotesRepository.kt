package com.amsavarthan.quotes.repository

import com.amsavarthan.quotes.data.api.QuotesApiService
import com.amsavarthan.quotes.data.api.Resource
import com.amsavarthan.quotes.data.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuotesRepository @Inject constructor(
    private val quotesApiService: QuotesApiService
) {

    suspend fun getRandomQuote(): Flow<Resource<Quote>> = flow {
        try {
            val response = quotesApiService.getRandomQuote()
            if (response.body() == null) {
                emit(Resource.Error())
                return@flow
            }
            emit(Resource.Success(response.body()!!))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error())
        }
    }

}