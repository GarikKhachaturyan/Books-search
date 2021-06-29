package com.example.booksearch.ui.dashboard.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.load
import coil.request.Disposable
import com.example.booksearch.R
import com.example.booksearch.model.Book

class BooksAdapter(context: Context) : ListAdapter<Book, BooksAdapter.BookHolder>(diffCallback) {

    private val separator = context.resources.getString(R.string.separator)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookHolder(view)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = currentList[position]
        holder.bind(book)
    }

    override fun getItemCount() = currentList.size

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class BookHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageView = view.findViewById<ImageView>(R.id.imageView)
        private val titleView = view.findViewById<TextView>(R.id.titleView)
        private val authorView = view.findViewById<TextView>(R.id.authorView)

        private var imageLoadDisposer: Disposable? = null

        fun bind(book: Book) {
            titleView.text = book.title
            authorView.text = book.authors.joinToString("$separator ")

            imageLoadDisposer?.dispose()

            if (book.thumbnailUrl != null) {
                imageLoadDisposer = imageView.load(book.thumbnailUrl) {
                    placeholder(R.drawable.ic_image_placeholder)
                    error(R.drawable.ic_image_placeholder)
                }
            } else {
                imageLoadDisposer = null
                imageView.load(R.drawable.ic_image_placeholder)
            }
        }
    }
}