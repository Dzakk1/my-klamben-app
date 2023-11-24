package com.example.myklamben.ui.view.cart

import com.example.myklamben.model.OrderItem

data class CartState(
    val orderItem: List<OrderItem>,
    val totalPrice : Int,
)
