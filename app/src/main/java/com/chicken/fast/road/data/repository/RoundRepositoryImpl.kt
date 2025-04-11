package com.chicken.fast.road.data.repository

import com.chicken.fast.road.data.dao.RoundDao
import com.chicken.fast.road.data.entity.Round
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoundRepositoryImpl(
    private val dao: RoundDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RoundRepository {
    override suspend fun delete(round: Round) = withContext(dispatcher) {
        dao.delete(round)
    }

    override suspend fun getById(id: Int): Round? = withContext(dispatcher) {
        dao.getById(id)
    }

    override suspend fun getAll(): List<Round> = withContext(dispatcher) {
        dao.getAll()
    }

    override suspend fun getMostFive(): List<Round> = withContext(dispatcher) {
        dao.getMostFive()
    }

    override suspend fun insert(round: Round): Long = withContext(dispatcher) {
        dao.insert(round)
    }

    override suspend fun update(round: Round) = withContext(dispatcher) {
        dao.update(round)
    }

    override suspend fun deleteById(id: Int) = withContext(dispatcher) {
        dao.deleteById(id)
    }

    override suspend fun deleteAll() = withContext(dispatcher) {
        dao.deleteAll()
    }
}