package com.example.booksearch.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val thumbnailUrl: String?
)