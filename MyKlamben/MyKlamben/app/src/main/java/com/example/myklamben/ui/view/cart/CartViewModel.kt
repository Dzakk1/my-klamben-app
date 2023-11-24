package com.example.myklamben.ui.view.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myklamben.data.OrderRepository
import com.example.myklamben.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel (private val repository: OrderRepository) : ViewModel() {
    private val _uiState : MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderItems() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderItems()
                .collect { orderItem ->
                    val totalPrice = orderItem.sumOf { it.shopItem.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderItem, totalPrice))
                }
        }
    }

    fun updateOrderItem(itemId : Long, count : Int) {
        viewModelScope.launch {
            repository.updateOrderItem(itemId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderItems()
                    }
                }
        }
    }
}