package com.example.myklamben.ui.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myklamben.data.OrderRepository
import com.example.myklamben.model.OrderItem
import com.example.myklamben.model.ShopItem
import com.example.myklamben.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailItemViewModel (
    private val repository: OrderRepository
) : ViewModel () {

    private val _uiState: MutableStateFlow<UiState<OrderItem>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderItem>>
        get() = _uiState

    fun getItemById(shopItemId : Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderItemById(shopItemId))
        }
    }

    fun addToCart(shopItem: ShopItem, count : Int) {
        viewModelScope.launch {
            repository.updateOrderItem(shopItem.id, count)
        }
    }
}