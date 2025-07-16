package com.example.myapplication3.data.api

import com.example.myapplication3.data.model.NaverSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverSearchApi {
    @GET("v1/search/shop.json")
    suspend fun searchShopping(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int
    ): NaverSearchResponse
}