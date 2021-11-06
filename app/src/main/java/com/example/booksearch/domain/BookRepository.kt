package com.example.booksearch.domain

import com.example.booksearch.domain.models.Book
import com.example.booksearch.domain.models.SearchOption
import com.example.booksearch.domain.models.SearchOptionsData
import com.example.booksearch.util.DataState
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(searchText: String, searchOption: SearchOption): Flow<DataState<List<Book>>>

    fun getBookSearchOptions(): SearchOptionsData

    fun setBookSearchOption(searchOption: SearchOption)
}