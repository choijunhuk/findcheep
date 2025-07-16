package com.example.myapplication3.di

import com.example.myapplication3.data.api.ApiClient
import com.example.myapplication3.data.api.NaverSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideNaverSearchApi(): NaverSearchApi {
        return ApiClient.instance
    }
}