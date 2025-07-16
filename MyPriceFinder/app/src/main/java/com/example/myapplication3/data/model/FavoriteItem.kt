package com.example.myapplication3.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteItem(
    val name: String,
    val price: String,
    val imageUrl: String,
    @PrimaryKey val productLink: String
)