package com.example.optimize_application.database

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
    fun getAllGames(): LiveData<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: GameEntity)

    @Delete
    suspend fun deleteGame(game: GameEntity)

    @Query("SELECT * FROM game_table WHERE id = :gameId")
    fun getGameById(gameId: Int): LiveData<GameEntity>

    @Update
    suspend fun updateGame(game: GameEntity)
}
