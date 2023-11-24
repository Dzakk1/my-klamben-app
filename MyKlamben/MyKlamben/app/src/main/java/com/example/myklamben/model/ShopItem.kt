package com.example.myklamben.model

data class ShopItem(
    val id : Long,
    val image : Int,
    val title: String,
    val merk: String,
    val description : String,
    val price : Int,
)