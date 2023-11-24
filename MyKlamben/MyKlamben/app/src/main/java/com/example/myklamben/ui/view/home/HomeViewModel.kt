package com.example.myklamben.ui.view.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myklamben.data.OrderRepository
import com.example.myklamben.model.OrderItem
import com.example.myklamben.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: OrderRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderItem>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderItem>>>
        get() = _uiState


    fun getAllItems() {
        viewModelScope.launch {
            repository.getAllItems()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderItems ->
                    _uiState.value = UiState.Success(orderItems)
                }
        }
    }

    // search
    private val _query = mutableStateOf("")
    val query : State<String> get() = _query

    fun search(newQuery : String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.searchItem(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { data ->
                    _uiState.value = UiState.Success(data)
                }


        }
    }
    // end search
}