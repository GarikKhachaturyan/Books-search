package com.example.booksearch.model

import androidx.annotation.StringRes
import com.example.booksearch.R

enum class SearchOption(
    @StringRes
    val description: Int
) {
    BY_ALL(R.string.search_by_all),
    BY_AUTHOR(R.string.search_by_author),
    BY_TITLE(R.string.search_by_title),
    BY_GENRE(R.string.search_by_genre),
    BY_PUBLISHER(R.string.search_by_publisher);
}