package com.example.booksearch.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearch.R
import com.example.booksearch.model.SearchOption
import com.example.booksearch.ui.util.setVisible

class SearchOptionsAdapter : ListAdapter<SearchOptionsAdapter.Option, SearchOptionsAdapter.OptionHolder>(diffCallback) {

    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_option, parent, false)
        return OptionHolder(view)
    }

    override fun onBindViewHolder(holder: OptionHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun submitList(selectedPosition: Int, searchOptions: List<SearchOption>) {
        val options = searchOptions.mapIndexed { index, searchOption ->
            Option(index == selectedPosition, searchOption)
        }
        submitList(options)
    }

    interface ItemClickListener {
        fun onClick(searchOption: SearchOption)
    }

    data class Option(
        var selected: Boolean,
        var searchOption: SearchOption
    )

    inner class OptionHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleView = view.findViewById<TextView>(R.id.titleView)
        private val checkView = view.findViewById<View>(R.id.checkView)

        init {
            view.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position < 0) return@setOnClickListener

                itemClickListener?.onClick(getItem(position).searchOption)
            }
        }

        fun bind(option: Option) {
            titleView.setText(option.searchOption.description)
            checkView.setVisible(option.selected)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Option>() {
            override fun areItemsTheSame(oldItem: Option, newItem: Option): Boolean {
                return oldItem.searchOption == newItem.searchOption
            }

            override fun areContentsTheSame(oldItem: Option, newItem: Option): Boolean {
                return oldItem == newItem
            }
        }
    }
}