package com.chicken.fast.road.ui.base

import android.media.MediaPlayer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chicken.fast.road.R
import com.chicken.fast.road.ui.theme.brush
import com.chicken.fast.road.ui.theme.gradientBrush
import com.chicken.fast.road.util.SharPreference

@Composable
fun BaseHomeButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val context = LocalContext.current
    val sharPref = SharPreference(context)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        label = "button_scale"
    )
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.click_sound) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (sharPref.getSoundEnabled()) {
                        mediaPlayer.start()
                    }
                    onClick()
                }
            )
    ) {
        Image(
            painter = painterResource(R.drawable.img_btn_bg),
            contentDescription = null,
            modifier = Modifier
        )

        Text(
            text = text,
            style = TextStyle(
                brush = gradientBrush,
            ),
            fontFamily = FontFamily(Font(R.font.jaro)),
            fontSize = 28.sp,
            fontWeight = FontWeight.W900
        )
    }
}