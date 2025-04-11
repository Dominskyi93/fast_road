package com.chicken.fast.road.ui.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chicken.fast.road.R
import com.chicken.fast.road.navigation.Route
import com.chicken.fast.road.ui.base.BaseHomeButton

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    var clickable by remember { mutableStateOf(true) }

    BackHandler {
        (context as Activity).moveTaskToBack(false)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.img_big_chicken),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            BaseHomeButton(
                text = "GAME"
            ) {
                if (clickable) {
                    clickable = false
                    navController.navigate(Route.GAME.route)
                }
            }

            BaseHomeButton(
                text = "RECORDS"
            ) {
                if (clickable) {
                    clickable = false
                    navController.navigate(Route.RECORDS.route)
                }
            }

            BaseHomeButton(
                text = "CUSTOM"
            ) {
                if (clickable) {
                    clickable = false
                    navController.navigate(Route.CUSTOM.route)
                }
            }
        }
    }
}

