package com.example.booksearch.domain

import com.example.booksearch.network.BookService
import com.example.booksearch.network.toBook
import com.example.booksearch.util.DataState
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class BookRepositoryImpl
@Inject constructor(
    private val bookService: BookService
) : BookRepository {

    override fun getBooks(searchParam: BookSearchParam) = flow {
        emit(DataState.Loading)
        try {
            val booksData = bookService.getBooks(searchParam.name)
            val books = booksData.items?.mapNotNull { it.toBook() }
            emit(DataState.Success(books ?: emptyList()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}