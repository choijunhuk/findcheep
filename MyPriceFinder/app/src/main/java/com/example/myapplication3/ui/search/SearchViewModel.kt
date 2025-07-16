package com.example.myapplication3.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication3.data.model.SearchHistory
import com.example.myapplication3.domain.model.Product
import com.example.myapplication3.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val query: StateFlow<String> = savedStateHandle.getStateFlow(KEY_QUERY, "")

    val products: Flow<PagingData<Product>> = query
        .filter { it.isNotBlank() }
        .flatMapLatest { currentQuery ->
            getProductsUseCase(currentQuery)
        }.cachedIn(viewModelScope)

    val searchHistory: StateFlow<List<SearchHistory>> = getSearchHistoryUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSearchQuery(newQuery: String) {
        if (newQuery.isNotBlank()) {
            savedStateHandle[KEY_QUERY] = newQuery
            viewModelScope.launch {
                saveSearchHistoryUseCase(newQuery)
            }
        }
    }

    fun deleteSearchHistory(query: String) {
        viewModelScope.launch {
            deleteSearchHistoryUseCase(query)
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(product)
        }
    }

    companion object {
        private const val KEY_QUERY = "search_query"
    }
}