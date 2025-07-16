package com.example.myapplication3.domain.usecase

import com.example.myapplication3.data.model.SearchHistory
import com.example.myapplication3.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(): Flow<List<SearchHistory>> = repository.getSearchHistory()
}