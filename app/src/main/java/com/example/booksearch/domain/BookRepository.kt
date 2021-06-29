package com.example.booksearch.domain

import com.example.booksearch.model.Book
import com.example.booksearch.util.DataState
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(searchParam: BookSearchParam): Flow<DataState<List<Book>>>

}