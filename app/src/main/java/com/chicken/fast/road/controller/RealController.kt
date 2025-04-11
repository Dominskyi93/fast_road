package com.chicken.fast.road.controller

class RealController(
    private val scoreDataSource: ScoreDataSource
) : AppController {
    override fun updateScore(score: Int) {
        val currentScore = scoreDataSource.scoreStateFlow.value
        val newScore = currentScore + score
        scoreDataSource.updateScore(newScore)
    }
}