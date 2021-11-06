package com.example.booksearch.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksearch.databinding.FragmentSearchOptionsBinding
import com.example.booksearch.domain.models.SearchOption
import com.example.booksearch.domain.models.SearchOptionsData
import com.example.booksearch.ui.base.BaseFragment
import com.example.booksearch.ui.dashboard.adapter.SearchOptionsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchOptionsFragment : BaseFragment() {

    private val searchViewModel: SearchOptionsViewModel by viewModels()

    private var binding: FragmentSearchOptionsBinding? = null

    private lateinit var searchOptionsAdapter: SearchOptionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchOptionsAdapter = SearchOptionsAdapter()

        searchViewModel.searchOptionsData.observe(this) { searchOptionsData ->
            submitOptions(searchOptionsData)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentSearchOptionsBinding.inflate(inflater, container, false).also {
            binding = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        initOptionsList()
    }

    private fun initOptionsList() {
        val optionsList = binding?.optionsList ?: return

        optionsList.adapter = searchOptionsAdapter
        optionsList.layoutManager = LinearLayoutManager(requireContext())
        searchOptionsAdapter.itemClickListener = object : SearchOptionsAdapter.ItemClickListener {
            override fun onClick(searchOption: SearchOption) {
                searchViewModel.setSearchOption(searchOption)
            }
        }

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        optionsList.addItemDecoration(dividerItemDecoration)
    }

    private fun submitOptions(searchOptionsData: SearchOptionsData) {
        searchOptionsAdapter.submitList(searchOptionsData.selectedOptionOrdinal, searchOptionsData.searchOptions)
    }
}