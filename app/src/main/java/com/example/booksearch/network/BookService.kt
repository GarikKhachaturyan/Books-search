package com.example.booksearch.network

import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {

    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): BooksDataDto
}