package com.example.myapplication3.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.data.model.SearchHistory
import com.example.myapplication3.databinding.ItemSearchHistoryBinding

class SearchHistoryAdapter(
    private val onClick: (String) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : ListAdapter<SearchHistory, SearchHistoryAdapter.HistoryViewHolder>(HistoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding, onClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HistoryViewHolder(
        private val binding: ItemSearchHistoryBinding,
        private val onClick: (String) -> Unit,
        private val onDeleteClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: SearchHistory) {
            binding.chipQuery.text = history.query
            binding.chipQuery.setOnClickListener { onClick(history.query) }
            binding.chipQuery.setOnCloseIconClickListener { onDeleteClick(history.query) }
        }
    }
}

object HistoryDiffCallback : DiffUtil.ItemCallback<SearchHistory>() {
    override fun areItemsTheSame(oldItem: SearchHistory, newItem: SearchHistory) = oldItem.query == newItem.query
    override fun areContentsTheSame(oldItem: SearchHistory, newItem: SearchHistory) = oldItem == newItem
}