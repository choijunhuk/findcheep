package com.example.myapplication3.data.local

import androidx.room.*
import com.example.myapplication3.data.model.FavoriteItem
import com.example.myapplication3.data.model.RecentlyViewedItem
import com.example.myapplication3.data.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(item: FavoriteItem)
    @Query("DELETE FROM FavoriteItem WHERE productLink = :productLink")
    suspend fun deleteFavorite(productLink: String)
    @Query("SELECT * FROM FavoriteItem ORDER BY name ASC")
    fun getFavorites(): Flow<List<FavoriteItem>>
    @Query("SELECT EXISTS(SELECT * FROM FavoriteItem WHERE productLink = :productLink)")
    suspend fun isFavorite(productLink: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(history: SearchHistory)
    @Query("DELETE FROM SearchHistory WHERE query = :query")
    suspend fun deleteSearchHistory(query: String)
    @Query("SELECT * FROM SearchHistory ORDER BY timestamp DESC")
    fun getSearchHistory(): Flow<List<SearchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyViewed(item: RecentlyViewedItem)
}