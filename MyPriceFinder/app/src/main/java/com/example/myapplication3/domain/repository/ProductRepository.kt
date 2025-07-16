package com.example.myapplication3.domain.repository

import androidx.paging.PagingData
import com.example.myapplication3.data.model.FavoriteItem
import com.example.myapplication3.data.model.SearchHistory
import com.example.myapplication3.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getSearchProducts(query: String): Flow<PagingData<Product>>
    fun getFavorites(): Flow<List<FavoriteItem>>
    suspend fun addFavorite(product: Product)
    suspend fun removeFavorite(productLink: String)
    fun getSearchHistory(): Flow<List<SearchHistory>>
    suspend fun saveSearchQuery(query: String)
    suspend fun deleteSearchHistory(query: String)
    suspend fun addRecentlyViewed(product: Product)
}
