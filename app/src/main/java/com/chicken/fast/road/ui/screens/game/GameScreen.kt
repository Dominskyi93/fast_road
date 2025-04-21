package com.chicken.fast.road.ui.screens.game

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chicken.fast.road.R
import com.chicken.fast.road.controller.LocalAppController
import com.chicken.fast.road.navigation.Route
import com.chicken.fast.road.ui.base.BaseHomeButton
import com.chicken.fast.road.ui.base.BorderedTitle
import com.chicken.fast.road.ui.base.CircleButton
import com.chicken.fast.road.ui.theme.gradientBrush
import com.chicken.fast.road.ui.theme.yellowBrush
import com.chicken.fast.road.util.SharPreference
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class GameState {
    RUNNING, GAME_OVER
}

@Composable
fun GameScreen(navController: NavController, viewModel: GameVM = hiltViewModel()) {
    val controller = LocalAppController.current
    var gameState by remember { mutableStateOf(GameState.RUNNING) }
    var score by remember { mutableIntStateOf(0) }

    if (gameState == GameState.RUNNING) {
        RunningGame {
            score = it
            gameState = GameState.GAME_OVER
            controller.updateScore(score)
            viewModel.save(it)
        }
    } else {
        GameOverScreen(
            score = score,
            navController = navController,
            onRestart = {
                gameState = GameState.RUNNING
                score = 0
            }
        )
    }
}

@Composable
fun RunningGame(
    modifier: Modifier = Modifier,
    viewModel: GameVM = hiltViewModel(),
    onGameOver: (Int) -> Unit
) {
    val context = LocalContext.current
    val sharPref = SharPreference(context)
    val isHardDifficult by remember { mutableStateOf(sharPref.getDifficult()) }
    val delay = if (isHardDifficult) 700L else 1000L
    var score by remember { mutableIntStateOf(0) }
    val gameItems: MutableState<List<GameItem>> =
        remember { mutableStateOf(List(12) { GameItem.EMPTY }) }
    var seconds by remember { mutableIntStateOf(60) }
    var isRunning by remember { mutableStateOf(false) }
    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

    LaunchedEffect(seconds, isRunning) {
        if (isRunning) {
            while (seconds != 0) {
                delay(1000)
                seconds--
            }
        }
    }

    LaunchedEffect(isRunning) {
        if (SharPreference(context).getMusicEnabled() && isRunning) {
            val player = MediaPlayer.create(context, R.raw.music)
            player?.isLooping = true
            player?.start()
            mediaPlayer.value = player
        }

        if (isRunning) {
            while (seconds != 0) {
                delay(delay)
                val newItems = gameItems.value.toMutableList()
                newItems.indices.forEach { newItems[it] = GameItem.EMPTY }
                val itemsCount = Random.nextInt(2, 5)
                repeat(itemsCount) {
                    val randomIndex = Random.nextInt(0, 12)
                    val randomItem = viewModel.getRandomItem(context)
                    newItems[randomIndex] = randomItem
                }
                gameItems.value = newItems
            }
            onGameOver(score)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.value?.stop()
            mediaPlayer.value?.release()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = "SCORE: $score",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )

            Text(
                text = "$seconds",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            )
        }


        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                itemsIndexed(
                    items = gameItems.value,
                    key = { index, _ -> index }
                ) { index, item ->
                    GameCircle(
                        item = item,
                        onClick = {
                            score += item.score
                            val updatedItems = gameItems.value.toMutableList()
                            updatedItems[index] = GameItem.EMPTY
                            gameItems.value = updatedItems
                        }
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            if (!isRunning) {
                BaseHomeButton("GO!") {
                    isRunning = true
                }
            }
        }
    }
}

@Composable
fun GameCircle(item: GameItem, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is androidx.compose.foundation.interaction.PressInteraction.Press) {
                onClick()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(CircleShape)
            .then(
                if (item == GameItem.EMPTY) {
                    Modifier
                } else {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick
                    )
                }
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.img_circle_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        if (item != GameItem.EMPTY) {
            Image(
                painter = painterResource(item.image!!),
                contentDescription = null
            )
        }
    }
}

@Composable
fun GameOverScreen(
    score: Int,
    navController: NavController,
    onRestart: () -> Unit
) {
    var clickable by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(R.drawable.img_box_bg),
                contentDescription = null
            )

            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 40.dp)
            ) {
                BorderedTitle(text = "Game Over", brush = gradientBrush)
                Text(
                    text = "Your Result:",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.W900,
                    modifier = Modifier.padding(18.dp)
                )
                BorderedTitle("$score", brush = yellowBrush)

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    CircleButton(
                        image = R.drawable.img_circle_btn_home,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (clickable) {
                            clickable = false
                            navController.navigate(Route.HOME.route) {
                                popUpTo(Route.HOME.route)
                            }
                        }

                    }
                    CircleButton(
                        image = R.drawable.img_circle_btn_restart,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (clickable) {
                            clickable = false
                            onRestart()
                        }
                    }
                }
            }
        }
    }
}