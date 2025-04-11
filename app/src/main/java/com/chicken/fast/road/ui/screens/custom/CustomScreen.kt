package com.chicken.fast.road.ui.screens.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chicken.fast.road.R
import com.chicken.fast.road.ui.base.BorderedTitle
import com.chicken.fast.road.util.SharPreference

@Composable
fun CustomScreen() {
    val context = LocalContext.current
    val choices =
        listOf(R.drawable.img_chicken_1, R.drawable.img_chicken_2, R.drawable.img_chicken_3)

    var selectedIndex by remember { mutableStateOf(SharPreference(context).getSkin()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {

        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.img_box_bg),
                contentDescription = null
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BorderedTitle(
                    text = "CUSTOM"
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TriangleArrow(
                        modifier = Modifier.size(28.dp),
                        direction = ArrowDirection.LEFT
                    ) {
                        selectedIndex = (selectedIndex - 1 + choices.size) % choices.size
                        SharPreference(context).saveSkin(selectedIndex)
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(choices[selectedIndex]),
                            contentDescription = "Selected Skin",
                            modifier = Modifier.size(150.dp)
                        )
                    }

                    TriangleArrow(
                        modifier = Modifier.size(28.dp),
                        direction = ArrowDirection.RIGHT
                    ) {
                        selectedIndex = (selectedIndex + 1) % choices.size
                        SharPreference(context).saveSkin(selectedIndex)
                    }

                }
            }

        }
    }
}

enum class ArrowDirection { LEFT, RIGHT }

@Composable
fun TriangleArrow(modifier: Modifier, direction: ArrowDirection, onClick: () -> Unit) {
    androidx.compose.foundation.Canvas(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    ) {
        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.WHITE
                style = android.graphics.Paint.Style.FILL
            }
            val path = Path().apply {
                if (direction == ArrowDirection.LEFT) {
                    moveTo(size.width, 0f)
                    lineTo(0f, size.height / 2)
                    lineTo(size.width, size.height)
                } else {
                    moveTo(0f, 0f)
                    lineTo(size.width, size.height / 2)
                    lineTo(0f, size.height)
                }
                close()
            }
            canvas.nativeCanvas.drawPath(path.asAndroidPath(), paint)
        }
    }
}