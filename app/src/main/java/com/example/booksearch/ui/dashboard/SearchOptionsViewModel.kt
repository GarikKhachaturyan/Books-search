package com.example.booksearch.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.booksearch.domain.BookRepository
import com.example.booksearch.domain.models.SearchOption
import com.example.booksearch.domain.models.SearchOptionsData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchOptionsViewModel
@Inject constructor(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val searchOptionsData = MutableLiveData<SearchOptionsData>()

    init {
        reloadSearchOptions()
    }

    fun setSearchOption(searchOption: SearchOption) {
        bookRepository.setBookSearchOption(searchOption)
        reloadSearchOptions()
    }

    private fun reloadSearchOptions() {
        val searchOptions = bookRepository.getBookSearchOptions()
        searchOptionsData.value = searchOptions
    }
}