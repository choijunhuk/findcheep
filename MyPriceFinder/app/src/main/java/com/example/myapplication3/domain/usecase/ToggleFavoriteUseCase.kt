package com.example.myapplication3.domain.usecase

import com.example.myapplication3.domain.model.Product
import com.example.myapplication3.domain.repository.ProductRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(product: Product) {
        if (product.isFavorite) {
            repository.removeFavorite(product.productLink)
        } else {
            repository.addFavorite(product)
        }
        product.isFavorite = !product.isFavorite
    }
}