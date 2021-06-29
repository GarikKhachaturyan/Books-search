package com.example.booksearch.di

import com.example.booksearch.domain.BookRepository
import com.example.booksearch.domain.BookRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class BookRepositoryModule {

    @ViewModelScoped
    @Binds
    abstract fun provideBookRepository(
        bookRepository: BookRepositoryImpl
    ): BookRepository

}