package com.example.booksearch.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import coil.load
import com.example.booksearch.R
import com.example.booksearch.databinding.FragmentBookDetailsBinding
import com.example.booksearch.model.Book
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailsFragment : Fragment() {

    private var binding: FragmentBookDetailsBinding? = null

    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        book = requireArguments().getParcelable("book")!!

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentBookDetailsBinding.inflate(inflater, container, false).also {
            binding = it
        }
        binding.imageView.transitionName = book.id
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = binding ?: return

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.titleView.text = book.title
        val separator = resources.getString(R.string.separator)
        binding.authorView.text = book.authors.joinToString("$separator ")

        binding.descriptionView.text = book.description

        if (book.thumbnailUrl != null) {
            binding.imageView.load(book.thumbnailUrl) {
                placeholder(R.drawable.ic_image_placeholder)
                error(R.drawable.ic_image_placeholder)
            }
        } else {
            binding.imageView.load(R.drawable.ic_image_placeholder)
        }
    }
}