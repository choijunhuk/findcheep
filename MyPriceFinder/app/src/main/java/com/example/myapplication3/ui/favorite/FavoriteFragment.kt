package com.example.myapplication3.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication3.databinding.FragmentFavoriteBinding
import com.example.myapplication3.domain.model.Product
import com.example.myapplication3.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter(
            onItemClick = { favoriteItem ->
                val product = Product(favoriteItem.name, favoriteItem.price, favoriteItem.imageUrl, favoriteItem.productLink, true)
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("product", product)
                }
                startActivity(intent)
            },
            onFavoriteClick = { favoriteItem ->
                viewModel.removeFavorite(favoriteItem.productLink)
            }
        )
        binding.recyclerFavorites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favorites.collectLatest { favorites ->
                favoriteAdapter.submitList(favorites)
                binding.layoutEmptyFavorites.visibility = if (favorites.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}