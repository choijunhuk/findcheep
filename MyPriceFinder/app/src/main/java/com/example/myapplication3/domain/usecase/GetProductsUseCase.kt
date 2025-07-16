package com.example.myapplication3.domain.usecase

import androidx.paging.PagingData
import com.example.myapplication3.domain.model.Product
import com.example.myapplication3.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(query: String): Flow<PagingData<Product>> = repository.getSearchProducts(query)
}