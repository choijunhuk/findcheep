package com.example.myapplication3.domain.usecase

import com.example.myapplication3.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteSearchHistoryUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(query: String) = repository.deleteSearchHistory(query)
}