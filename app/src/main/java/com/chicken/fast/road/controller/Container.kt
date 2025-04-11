package com.chicken.fast.road.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

val LocalAppScore = compositionLocalOf {
    0
}

val LocalAppController: ProvidableCompositionLocal<AppController> =
    staticCompositionLocalOf {
        EmptyController
    }

@Composable
fun Container(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val themeDataSource = remember {
        SharedPreferenceScoreDataSource(context)
    }
    val controller = remember {
        RealController(themeDataSource)
    }

    val theme by themeDataSource.scoreStateFlow.collectAsStateWithLifecycle()
    CompositionLocalProvider(
        LocalAppScore provides theme,
        LocalAppController provides controller
    ) {
        content()
    }
}