package com.example.myklamben.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myklamben.data.OrderRepository
import com.example.myklamben.ui.view.cart.CartViewModel
import com.example.myklamben.ui.view.detail.DetailItemViewModel
import com.example.myklamben.ui.view.home.HomeViewModel

class ViewModelFactory (private val repository: OrderRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailItemViewModel::class.java)) {
            return DetailItemViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}