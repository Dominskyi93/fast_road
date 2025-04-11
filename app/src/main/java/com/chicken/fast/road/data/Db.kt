package com.chicken.fast.road.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chicken.fast.road.data.dao.RoundDao
import com.chicken.fast.road.data.entity.Round

@Database(
    entities = [Round::class],
    version = 1,
    exportSchema = false
)
abstract class Db : RoomDatabase() {
    abstract fun roundDao(): RoundDao
}