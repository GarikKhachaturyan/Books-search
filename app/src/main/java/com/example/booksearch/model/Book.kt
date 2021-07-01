package com.example.booksearch.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val description: String?,
    val thumbnailUrl: String?
) : Parcelable