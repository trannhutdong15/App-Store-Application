package com.example.app_store_application.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGames(games: List<GameEntity>)

    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<GameEntity>
}

