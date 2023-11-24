package com.example.myklamben.data

import com.example.myklamben.model.FakeItemsDataSource
import com.example.myklamben.model.OrderItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class OrderRepository {
    private val orderItems = mutableListOf<OrderItem>()

    init {
        if (orderItems.isEmpty()) {
            FakeItemsDataSource.dummyItems.forEach {
                orderItems.add(OrderItem(it, 0))
            }
        }
    }

    fun getAllItems(): Flow<List<OrderItem>> {
        return flowOf(orderItems)
    }

    fun getOrderItemById(itemId : Long) : OrderItem {
        return orderItems.first {
            it.shopItem.id == itemId
        }
    }


    fun searchItem(query: String): Flow<List<OrderItem>> {
        return flowOf(
            orderItems.filter { orderItem ->
                orderItem.shopItem.title.contains(query, ignoreCase = true)
            }
        )
    }


    fun updateOrderItem(itemId: Long, newCountValue: Int) : Flow<Boolean> {
        val index = orderItems.indexOfFirst { it.shopItem.id == itemId }
        val result = if (index >= 0) {
            val orderItem = orderItems[index]
            orderItems[index] = orderItem.copy(shopItem = orderItem.shopItem, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderItems() : Flow<List<OrderItem>> {
        return getAllItems()
            .map { orderItems ->
                orderItems.filter { orderItem -> orderItem.count != 0 }
            }
    }

    companion object {
        @Volatile
        private var instance : OrderRepository?= null

        fun getInstance() : OrderRepository =
            instance ?: synchronized(this) {
                OrderRepository().apply {
                    instance = this
                }
            }
    }
}