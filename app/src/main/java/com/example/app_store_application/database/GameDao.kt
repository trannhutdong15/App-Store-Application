package com.example.app_store_application.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
//GameDao have 4 function which will get all game list , insert game ,delete game , update game
@Dao
interface GameDao {
    @Query("SELECT * FROM game_table")
    fun getAllGames(): List<GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: GameEntity)

    @Query("DELETE FROM GAME_TABLE WHERE id = :gameId")
    suspend fun deleteGameById(gameId: Int)

    @Query("SELECT * FROM game_table WHERE id = :gameId")
    fun getGameById(gameId: Int): LiveData<GameEntity>

    @Update
    suspend fun updateGame(game: GameEntity)
}
