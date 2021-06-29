package com.example.booksearch.network

class BookDto(
    val id: String? = null,
    val volumeInfo: BookInfoDto? = null,
)

class BookInfoDto(
    val title: String? = null,
    val subtitle: String? = null,
    val authors: List<String>? = null,
    val description: String? = null,
    val imageLinks: ImageLinksDto? = null
)

class ImageLinksDto(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)