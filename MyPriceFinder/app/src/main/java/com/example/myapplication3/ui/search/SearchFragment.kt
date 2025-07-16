package com.example.myapplication3.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication3.R
import com.example.myapplication3.databinding.FragmentSearchBinding
import com.example.myapplication3.domain.model.Product
import com.example.myapplication3.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var productAdapter: ProductPagingAdapter
    private lateinit var historyAdapter: SearchHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        productAdapter = ProductPagingAdapter(
            onItemClick = { product ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("product", product)
                }
                startActivity(intent)
            },
            onFavoriteClick = { product -> viewModel.toggleFavorite(product) }
        )
        binding.recyclerProducts.adapter = productAdapter

        productAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading

            val errorState = loadState.refresh as? LoadState.Error
            errorState?.let {
                val errorMessage = when (val error = it.error) {
                    is HttpException -> if (error.code() == 429) getString(R.string.error_rate_limit) else getString(R.string.error_fetch_api, error.message())
                    else -> getString(R.string.error_fetch_general, error.localizedMessage)
                }
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }

            val isListEmpty = loadState.refresh is LoadState.NotLoading && productAdapter.itemCount == 0
            binding.layoutEmpty.isVisible = isListEmpty
            binding.recyclerHistory.isVisible = isListEmpty && binding.etSearch.text.isEmpty()
        }

        historyAdapter = SearchHistoryAdapter(
            onClick = { query ->
                binding.etSearch.setText(query)
                binding.etSearch.setSelection(query.length)
                performSearch()
            },
            onDeleteClick = { query -> viewModel.deleteSearchHistory(query) }
        )
        binding.recyclerHistory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = historyAdapter
        }
    }

    private fun setupListeners() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@setOnEditorActionListener true
            }
            false
        }
        binding.btnSearch.setOnClickListener { performSearch() }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collectLatest { pagingData ->
                productAdapter.submitData(pagingData)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchHistory.collectLatest { history ->
                historyAdapter.submitList(history)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.query.collectLatest {
                if (binding.etSearch.text.toString() != it) {
                    binding.etSearch.setText(it)
                }
            }
        }
    }

    private fun performSearch() {
        val query = binding.etSearch.text.toString().trim()
        if (query.isNotEmpty()) {
            viewModel.setSearchQuery(query)
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        binding.etSearch.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerProducts.adapter = null
        _binding = null
    }
}