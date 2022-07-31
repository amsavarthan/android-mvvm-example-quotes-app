package com.amsavarthan.quotes.data.api

import com.amsavarthan.quotes.data.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApiService {

    @GET("/random")
    suspend fun getRandomQuote(): Response<Quote>

}