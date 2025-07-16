package com.example.myapplication3.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.myapplication3.BuildConfig
import com.example.myapplication3.data.api.NaverSearchApi
import com.example.myapplication3.data.local.AppDao
import com.example.myapplication3.data.model.FavoriteItem
import com.example.myapplication3.data.model.RecentlyViewedItem
import com.example.myapplication3.data.model.SearchHistory
import com.example.myapplication3.data.paging.ProductPagingSource
import com.example.myapplication3.domain.model.Product
import com.example.myapplication3.domain.repository.ProductRepository
import com.example.myapplication3.util.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val appDao: AppDao,
    private val api: NaverSearchApi
) : ProductRepository {

    override fun getSearchProducts(query: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = {
                ProductPagingSource(
                    api = api,
                    clientId = BuildConfig.NAVER_CLIENT_ID,
                    clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
                    query = query
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { naverShopItem ->
                val isFavorite = appDao.isFavorite(naverShopItem.link)
                naverShopItem.toDomain(isFavorite)
            }
        }
    }

    override fun getFavorites(): Flow<List<FavoriteItem>> = appDao.getFavorites()

    override suspend fun addFavorite(product: Product) {
        val favoriteItem = FavoriteItem(product.name, product.price, product.imageUrl, product.productLink)
        appDao.insertFavorite(favoriteItem)
    }

    override suspend fun removeFavorite(productLink: String) {
        appDao.deleteFavorite(productLink)
    }

    override fun getSearchHistory(): Flow<List<SearchHistory>> = appDao.getSearchHistory()

    override suspend fun saveSearchQuery(query: String) {
        if (query.isNotBlank()) {
            appDao.insertSearchHistory(SearchHistory(query = query, timestamp = System.currentTimeMillis()))
        }
    }

    override suspend fun deleteSearchHistory(query: String) {
        appDao.deleteSearchHistory(query)
    }

    override suspend fun addRecentlyViewed(product: Product) {
        val recentlyViewedItem = RecentlyViewedItem(
            productLink = product.productLink,
            name = product.name,
            price = product.price,
            imageUrl = product.imageUrl
        )
        appDao.insertRecentlyViewed(recentlyViewedItem)
    }
}