package com.example.booksearch.ui.dashboard

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksearch.R
import com.example.booksearch.databinding.FragmentBooksBinding
import com.example.booksearch.model.Book
import com.example.booksearch.ui.dashboard.adapter.BooksAdapter
import com.example.booksearch.ui.util.SimpleTextWatcher
import com.example.booksearch.ui.util.setVisible

class BooksFragment : Fragment() {

    private var binding: FragmentBooksBinding? = null

    private lateinit var booksAdapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        booksAdapter = BooksAdapter()
        addDummyList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentBooksBinding.inflate(inflater, container, false).also {
            binding = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initToolbar()

        initBooksList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchView = menu.findItem(R.id.searchView).actionView as SearchView
        searchView.queryHint = getString(R.string.search)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initToolbar() {
        val toolbar = binding?.toolbar ?: return

        val searchLayout = toolbar.menu.findItem(R.id.searchItem).actionView as ViewGroup
        val closeButton = searchLayout.findViewById<View>(R.id.closeButton)
        val searchButton = searchLayout.findViewById<View>(R.id.searchButton)
        val searchView = searchLayout.findViewById<EditText>(R.id.searchView)

        closeButton.setVisible(!searchView.text.isNullOrEmpty())
        searchView.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                closeButton.setVisible(!text.isNullOrEmpty())
                //TODO start search
            }
        })
        searchView.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO start search
                return@OnEditorActionListener true
            }
            false
        })
        closeButton.setOnClickListener {
            searchView.text = null
        }
        searchButton.setOnClickListener {
            //TODO start search
        }
    }

    private fun initBooksList() {
        val context = context ?: return
        val booksList = binding?.booksList ?: return

        booksList.adapter = booksAdapter

        booksList.layoutManager = LinearLayoutManager(context)
    }

    private fun addDummyList() {
        val books = mutableListOf(Book("wegf"), Book("werg"), Book("ilj"), Book("ewtur"))
        booksAdapter.submitList(books)
    }
}