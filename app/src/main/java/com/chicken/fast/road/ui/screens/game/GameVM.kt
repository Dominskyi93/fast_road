package com.chicken.fast.road.ui.screens.game

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.fast.road.R
import com.chicken.fast.road.data.entity.Round
import com.chicken.fast.road.data.repository.RoundRepository
import com.chicken.fast.road.util.SharPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameVM @Inject constructor(
    private val repository: RoundRepository
) : ViewModel() {

    fun save(score: Int) {
        viewModelScope.launch {
            val round = Round(score = score)
            repository.insert(round)
        }
    }

    fun getRandomItem(context: Context): GameItem {
        val skinList =
            listOf(R.drawable.img_chicken_1, R.drawable.img_chicken_2, R.drawable.img_chicken_3)
        val imageIndex = SharPreference(context).getSkin()
        val randomNumber = Random.nextInt(10)
        return when (randomNumber) {
            in 0..6 -> GameItem.Chicken(skinList[imageIndex], 1)
            in 7..8 -> GameItem.BOMB
            else -> GameItem.X2
        }
    }
}

sealed class GameItem(@DrawableRes val image: Int?, val score: Int = 0) {
    data object EMPTY : GameItem(null)
    data object X2 : GameItem(R.drawable.img_item_2, 2)
    data object BOMB : GameItem(R.drawable.img_item_1, -5)
    class Chicken(@DrawableRes image: Int, score: Int) : GameItem(image, score)
}