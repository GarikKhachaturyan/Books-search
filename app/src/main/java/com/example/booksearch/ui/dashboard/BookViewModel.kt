package com.example.booksearch.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksearch.domain.BookRepository
import com.example.booksearch.model.Book
import com.example.booksearch.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookViewModel
@Inject constructor(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val books = MutableLiveData<DataState<List<Book>>>()

    fun getBooks(searchText: String?) {
        if (searchText.isNullOrBlank()) {
            books.value = DataState.Success(emptyList())
            return
        }

        val bookSearchOptions = bookRepository.getBookSearchOptions()
        val searchOption = bookSearchOptions.searchOptions[bookSearchOptions.selectedOptionOrdinal]

        bookRepository.getBooks(searchText, searchOption)
            .onEach {dataState ->
                books.value = dataState
            }
            .launchIn(viewModelScope)
    }
}