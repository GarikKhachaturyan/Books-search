package com.example.booksearch.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booksearch.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}