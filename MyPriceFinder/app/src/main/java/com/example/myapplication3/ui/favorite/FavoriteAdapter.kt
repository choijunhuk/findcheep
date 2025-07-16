package com.example.myapplication3.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication3.R
import com.example.myapplication3.data.model.FavoriteItem
import com.example.myapplication3.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val onItemClick: (FavoriteItem) -> Unit,
    private val onFavoriteClick: (FavoriteItem) -> Unit
) : ListAdapter<FavoriteItem, FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, onItemClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavoriteViewHolder(
        private val binding: ItemFavoriteBinding,
        private val onItemClick: (FavoriteItem) -> Unit,
        private val onFavoriteClick: (FavoriteItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteItem) {
            binding.tvName.text = item.name
            binding.tvPrice.text = item.price
            Glide.with(binding.ivThumb.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(binding.ivThumb)

            binding.ivFavorite.setImageResource(R.drawable.ic_favorite_24)
            binding.ivFavorite.contentDescription = binding.ivFavorite.context.getString(R.string.desc_favorite_remove)

            binding.cardView.setOnClickListener { onItemClick(item) }
            binding.ivFavorite.setOnClickListener { onFavoriteClick(item) }
        }
    }
}

object FavoriteDiffCallback : DiffUtil.ItemCallback<FavoriteItem>() {
    override fun areItemsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem) = oldItem.productLink == newItem.productLink
    override fun areContentsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem) = oldItem == newItem
}