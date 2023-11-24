package com.example.myklamben.di

import com.example.myklamben.data.OrderRepository

object Injection {
    fun provideRepository(): OrderRepository {
        return OrderRepository.getInstance()
    }
}