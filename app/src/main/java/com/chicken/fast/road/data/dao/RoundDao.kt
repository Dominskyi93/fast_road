package com.chicken.fast.road.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chicken.fast.road.data.entity.Round

@Dao
interface RoundDao {
    @Query("SELECT * FROM Round WHERE id = :id")
    fun getById(id: Int): Round?

    @Query("SELECT * FROM Round")
    fun getAll(): List<Round>

    @Query("SELECT * FROM Round ORDER BY score DESC LIMIT 5")
    fun getMostFive(): List<Round>

    @Insert
    fun insert(round: Round): Long

    @Update
    fun update(round: Round)

    @Delete
    fun delete(round: Round)

    @Query("DELETE FROM Round WHERE id = :id")
    fun deleteById(id: Int)

    @Query("DELETE FROM Round")
    fun deleteAll()
}