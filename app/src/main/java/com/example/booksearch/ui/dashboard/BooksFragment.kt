package com.example.booksearch.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.example.booksearch.R
import com.example.booksearch.databinding.FragmentBooksBinding
import com.example.booksearch.domain.models.Book
import com.example.booksearch.ui.base.BaseFragment
import com.example.booksearch.ui.dashboard.adapter.BooksAdapter
import com.example.booksearch.ui.util.SimpleTextWatcher
import com.example.booksearch.ui.util.setVisible
import com.example.booksearch.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksFragment : BaseFragment() {

    private val bookViewModel: BookViewModel by viewModels()

    private var binding: FragmentBooksBinding? = null

    private lateinit var booksAdapter: BooksAdapter

    private val handler = Handler(Looper.getMainLooper())
    private var searchBooksCallback: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        booksAdapter = BooksAdapter(requireContext())

        bookViewModel.books.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<List<Book>> -> {
                    submitBooks(dataState.data)
                }
            }
        }

        exitTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.fade)
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

        binding?.noBookPlaceholderView?.setVisible(booksAdapter.itemCount == 0)
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

        val closeButton = toolbar.findViewById<View>(R.id.closeButton)
        val searchButton = toolbar.findViewById<View>(R.id.searchButton)
        val searchView = toolbar.findViewById<EditText>(R.id.searchView)
        val filterButton = toolbar.findViewById<View>(R.id.filterButton)

        closeButton.setVisible(!searchView.text.isNullOrEmpty())
        searchView.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                closeButton.setVisible(!text.isNullOrEmpty())

                searchBooksDelayed(text.toString())
            }
        })
        searchView.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                startSearchNow(searchView.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
        closeButton.setOnClickListener {
            searchView.text = null
        }
        searchButton.setOnClickListener {
            startSearchNow(searchView.text.toString())
        }
        filterButton.setOnClickListener {
            val action = BooksFragmentDirections.actionBooksFragmentToSearchOptionsFragment()
            findNavController().navigate(action)
        }
    }

    private fun initBooksList() {
        val context = context ?: return
        val booksList = binding?.booksList ?: return

        booksList.adapter = booksAdapter

        booksList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        booksList.addItemDecoration(dividerItemDecoration)

        booksAdapter.itemClickListener = object : BooksAdapter.ItemClickListener {
            override fun onItemClicked(sharedImageView: View, book: Book) {
                val action = BooksFragmentDirections.actionBooksFragmentToBookDetailsFragment(book)
                val extras = FragmentNavigatorExtras(
                    sharedImageView to book.id
                )
                findNavController().navigate(action, extras)
            }
        }
    }

    private fun startSearchNow(bookName: String?) {
        searchBooksCallback?.let {
            handler.removeCallbacks(it)
        }

        bookViewModel.getBooks(bookName)
    }

    private fun searchBooksDelayed(bookName: String?) {
        searchBooksCallback?.let {
            handler.removeCallbacks(it)
        }

        val searchBooksCallback = Runnable {
            bookViewModel.getBooks(bookName)
        }.apply {
            searchBooksCallback = this
        }
        handler.postDelayed(searchBooksCallback, 400L)
    }

    private fun submitBooks(books: List<Book>?) {
        booksAdapter.submitList(books) {
            binding?.noBookPlaceholderView?.setVisible(books.isNullOrEmpty())
        }
    }
}