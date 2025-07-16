package com.example.myapplication3.domain.usecase

import com.example.myapplication3.data.model.FavoriteItem
import com.example.myapplication3.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(): Flow<List<FavoriteItem>> = repository.getFavorites()
}