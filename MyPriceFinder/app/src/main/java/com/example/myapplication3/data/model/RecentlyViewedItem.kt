package com.example.myapplication3.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_viewed_items")
data class RecentlyViewedItem(
    @PrimaryKey val productLink: String,
    val name: String,
    val price: String,
    val imageUrl: String,
    val viewedAt: Long = System.currentTimeMillis()
)