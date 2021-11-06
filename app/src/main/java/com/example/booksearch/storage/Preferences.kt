package com.example.booksearch.storage

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.booksearch.domain.models.SearchOption
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preferences
@Inject constructor(@ApplicationContext context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getSelectedBookSearchOptionOrdinal(): Int {
        return preferences.getInt(KEY_SEARCH_OPTION, SearchOption.BY_ALL.ordinal)
    }

    fun setBookSearchOptionOrdinal(ordinal: Int) {
        preferences.edit {
            putInt(KEY_SEARCH_OPTION, ordinal)
        }
    }

    companion object {
        private const val KEY_SEARCH_OPTION = "SEARCH_OPTION"
    }
}