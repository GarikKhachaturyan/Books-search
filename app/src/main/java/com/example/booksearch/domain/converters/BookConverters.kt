package com.example.booksearch.domain.converters

import com.example.booksearch.domain.models.Book
import com.example.booksearch.network.models.BookDto

fun BookDto.toBook(): Book? {
    val id = id ?: return null
    val title = volumeInfo?.title ?: return null
    val authors = volumeInfo.authors ?: emptyList()
    var thumbnailUrl = volumeInfo.imageLinks?.thumbnail
    if (thumbnailUrl != null && !thumbnailUrl.startsWith("https")) {
        thumbnailUrl = thumbnailUrl.replace("http", "https")
    }
    return Book(id, title, authors, volumeInfo.description, thumbnailUrl)
}