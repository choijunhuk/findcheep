package com.example.myapplication3.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication3.data.model.FavoriteItem
import com.example.myapplication3.domain.usecase.GetFavoritesUseCase
import com.example.myapplication3.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val favorites: StateFlow<List<FavoriteItem>> = getFavoritesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFavorite(productLink: String) {
        viewModelScope.launch {
            // isFavorite가 true인 임시 Product 객체를 만들어 UseCase에 전달
            val dummyProduct = com.example.myapplication3.domain.model.Product("", "", "", productLink, true)
            toggleFavoriteUseCase(dummyProduct)
        }
    }
}