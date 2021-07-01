package com.example.booksearch.domain

import com.example.booksearch.model.Book
import com.example.booksearch.model.SearchOption
import com.example.booksearch.model.SearchOptionsData
import com.example.booksearch.util.DataState
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(searchText: String, searchOption: SearchOption): Flow<DataState<List<Book>>>

    fun getBookSearchOptions(): SearchOptionsData

    fun setBookSearchOption(searchOption: SearchOption)
}