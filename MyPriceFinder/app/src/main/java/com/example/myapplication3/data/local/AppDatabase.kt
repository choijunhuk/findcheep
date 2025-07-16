package com.example.myapplication3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
// import com.example.myapplication3.data.model.FavoriteItem // 이 import는 그대로 둡니다.
import com.example.myapplication3.data.model.RecentlyViewedItem
import com.example.myapplication3.data.model.SearchHistory

/*
// 이 부분을 전체 삭제하세요.
@Entity
data class FavoriteItem(
    val name: String,
    val price: String,
    val imageUrl: String,
    @PrimaryKey val productLink: String
)
*/

@Database(
    entities = [com.example.myapplication3.data.model.FavoriteItem::class, SearchHistory::class, RecentlyViewedItem::class], // 경로를 명확히 해줍니다.
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}