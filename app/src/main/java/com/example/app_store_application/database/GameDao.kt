package com.example.app_store_application.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: GameEntity)
    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<GameEntity>
}
