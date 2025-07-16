package com.example.myapplication3.data.model

data class NaverSearchResponse(
    val items: List<NaverShopItem>
)

data class NaverShopItem(
    val title: String,
    val link: String,
    val image: String,
    val lprice: String
)