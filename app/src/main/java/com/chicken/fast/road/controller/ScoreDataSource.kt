package com.chicken.fast.road.controller

import kotlinx.coroutines.flow.MutableStateFlow

interface ScoreDataSource {
    var scoreStateFlow: MutableStateFlow<Int>

    fun updateScore(score: Int)
}