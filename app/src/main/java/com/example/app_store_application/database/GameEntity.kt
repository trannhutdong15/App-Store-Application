package com.example.app_store_application.database

data class GameEntity(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val imageUrl: String
)