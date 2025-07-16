package com.example.myapplication3.domain.usecase

import com.example.myapplication3.domain.repository.ProductRepository
import javax.inject.Inject

class SaveSearchHistoryUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(query: String) = repository.saveSearchQuery(query)
}