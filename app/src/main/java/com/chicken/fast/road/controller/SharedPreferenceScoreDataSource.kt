package com.chicken.fast.road.controller

import android.content.Context
import com.chicken.fast.road.util.SharPreference
import kotlinx.coroutines.flow.MutableStateFlow

class SharedPreferenceScoreDataSource(context: Context) : ScoreDataSource {
    private val sharedPreference = SharPreference(context)

    override var scoreStateFlow: MutableStateFlow<Int> =
        MutableStateFlow(sharedPreference.getScore())

    override fun updateScore(score: Int) {
        sharedPreference.saveScore(score)
        scoreStateFlow.value = score
    }
}