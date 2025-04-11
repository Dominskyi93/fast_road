package com.chicken.fast.road.data.repository

import com.chicken.fast.road.data.entity.Round

interface RoundRepository {
    suspend fun getById(id: Int): Round?

    suspend fun getAll(): List<Round>

    suspend fun getMostFive(): List<Round>

    suspend fun insert(round: Round): Long

    suspend fun update(round: Round)

    suspend fun delete(round: Round)

    suspend fun deleteById(id: Int)

    suspend fun deleteAll()
}