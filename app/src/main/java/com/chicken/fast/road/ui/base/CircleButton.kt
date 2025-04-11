package com.chicken.fast.road.ui.base

import android.media.MediaPlayer
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.chicken.fast.road.R
import com.chicken.fast.road.util.SharPreference

@Composable
fun CircleButton(@DrawableRes image: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val context = LocalContext.current
    val sharPref = SharPreference(context)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.click_sound) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        label = "button_scale"
    )

    var lastClickTime by remember { mutableLongStateOf(0L) }

    val clickDelay = 500L

    Image(
        painter = painterResource(image),
        contentDescription = null,
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickTime > clickDelay) {
                        lastClickTime = currentTime
                        if (sharPref.getSoundEnabled()) {
                            mediaPlayer.start()
                        }
                        onClick()
                    }
                }
            )
    )
}