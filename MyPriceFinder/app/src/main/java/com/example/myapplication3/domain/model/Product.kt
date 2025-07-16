package com.example.myapplication3.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    val price: String,
    val imageUrl: String,
    val productLink: String,
    var isFavorite: Boolean
) : Parcelable