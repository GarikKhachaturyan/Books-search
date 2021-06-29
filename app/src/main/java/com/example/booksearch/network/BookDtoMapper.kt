package com.example.booksearch.network

import com.example.booksearch.model.Book

fun BookDto.toBook(): Book? {
    val id = id ?: return null
    val title = volumeInfo?.title ?: return null
    val authors = volumeInfo.authors ?: emptyList()
    var thumbnailUrl = volumeInfo.imageLinks?.thumbnail
    if (thumbnailUrl != null && !thumbnailUrl.startsWith("https")) {
        thumbnailUrl = thumbnailUrl.replace("http", "https")
    }
    return Book(id, title, authors, thumbnailUrl)
}