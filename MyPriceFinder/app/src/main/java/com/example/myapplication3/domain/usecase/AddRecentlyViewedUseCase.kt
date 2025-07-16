package com.example.myapplication3.domain.usecase

import com.example.myapplication3.domain.model.Product
import com.example.myapplication3.domain.repository.ProductRepository
import javax.inject.Inject

class AddRecentlyViewedUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(product: Product) = repository.addRecentlyViewed(product)
}