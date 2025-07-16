package com.example.myapplication3.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication3.R
import com.example.myapplication3.databinding.ItemProductBinding
import com.example.myapplication3.domain.model.Product

class ProductPagingAdapter(
    private val onItemClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : PagingDataAdapter<Product, ProductPagingAdapter.ProductViewHolder>(ProductDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onItemClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onItemClick: (Product) -> Unit,
        private val onFavoriteClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.tvName.text = item.name
            binding.tvPrice.text = item.price

            Glide.with(binding.ivThumb.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(binding.ivThumb)

            binding.ivFavorite.setImageResource(
                if (item.isFavorite) R.drawable.ic_favorite_24 else R.drawable.ic_favorite_border_24
            )

            val context = binding.ivFavorite.context
            binding.ivFavorite.contentDescription =
                if (item.isFavorite) context.getString(R.string.desc_favorite_remove)
                else context.getString(R.string.desc_favorite_add)

            binding.cardView.setOnClickListener { onItemClick(item) }
            binding.ivFavorite.setOnClickListener { onFavoriteClick(item) }
        }
    }
}

object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.productLink == newItem.productLink
    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}