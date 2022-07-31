package com.amsavarthan.quotes.repository

import com.amsavarthan.quotes.data.api.QuotesApiService
import javax.inject.Inject

class QuotesRepository @Inject constructor(
    private val quotesApiService: QuotesApiService
) {

    suspend fun getRandomQuote() = quotesApiService.getRandomQuote()

}