package com.example.myapplication3.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication3.domain.model.Product
import com.example.myapplication3.domain.usecase.AddRecentlyViewedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val addRecentlyViewedUseCase: AddRecentlyViewedUseCase
) : ViewModel() {

    fun addRecentlyViewed(product: Product) {
        viewModelScope.launch {
            addRecentlyViewedUseCase(product)
        }
    }
}