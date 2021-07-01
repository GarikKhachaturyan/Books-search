package com.example.booksearch.domain

import com.example.booksearch.model.SearchOption
import com.example.booksearch.model.SearchOptionsData
import com.example.booksearch.network.BookService
import com.example.booksearch.network.toBook
import com.example.booksearch.storage.Preferences
import com.example.booksearch.util.DataState
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class BookRepositoryImpl
@Inject constructor(
    private val bookService: BookService,
    private val preferences: Preferences
) : BookRepository {

    override fun getBooks(searchText: String, searchOption: SearchOption) = flow {
        emit(DataState.Loading)
        try {
            val booksData = bookService.getBooks(getSearchQuery(searchOption, searchText))
            val books = booksData.items?.mapNotNull { it.toBook() }
            emit(DataState.Success(books ?: emptyList()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override fun getBookSearchOptions(): SearchOptionsData {
        val selectedOptionOrdinal = preferences.getSelectedBookSearchOptionOrdinal()
        return SearchOptionsData(
            selectedOptionOrdinal,
            SearchOption.values().toList()
        )
    }

    override fun setBookSearchOption(searchOption: SearchOption) {
        preferences.setBookSearchOptionOrdinal(searchOption.ordinal)
    }

    private fun getSearchQuery(searchOption: SearchOption, searchText: String): String {
        return when (searchOption) {
            SearchOption.BY_ALL -> searchText
            SearchOption.BY_TITLE -> "intitle:$searchText"
            SearchOption.BY_AUTHOR -> "inauthor:$searchText"
            SearchOption.BY_PUBLISHER -> "inpublisher:$searchText"
            SearchOption.BY_GENRE -> "subject:$searchText"
        }
    }
}