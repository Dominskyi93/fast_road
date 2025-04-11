package com.chicken.fast.road.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Round(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val score: Int
)
