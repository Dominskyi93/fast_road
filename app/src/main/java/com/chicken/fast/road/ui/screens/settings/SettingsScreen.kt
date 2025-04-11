package com.chicken.fast.road.ui.screens.settings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chicken.fast.road.R
import com.chicken.fast.road.ui.base.BorderedTitle
import com.chicken.fast.road.ui.theme.Brown
import com.chicken.fast.road.ui.theme.Pink
import com.chicken.fast.road.ui.theme.Yellow
import com.chicken.fast.road.util.SharPreference

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val sharPreference = SharPreference(context)
    var isHard by remember { mutableStateOf(sharPreference.getDifficult()) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.img_box_bg),
            contentDescription = null
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
        ) {
            BorderedTitle(text = "SETTINGS", modifier = Modifier)

            Spacer(Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Music:",
                    fontFamily = FontFamily(Font(R.font.jaro)),
                    fontWeight = FontWeight.W900,
                    color = Color.White,
                    fontSize = 24.sp
                )

                var checkedMusic by remember { mutableStateOf(sharPreference.getMusicEnabled()) }
                CustomSwitch(
                    checked = checkedMusic,
                    onCheckedChange = {
                        checkedMusic = !checkedMusic
                        sharPreference.saveMusicEnabled(checkedMusic)
                    },
                    colors = CustomSwitchDefaults.colors(
                        checkedThumbColor = Yellow,
                        uncheckedThumbColor = Yellow
                    ),
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sound:",
                    fontFamily = FontFamily(Font(R.font.jaro)),
                    fontWeight = FontWeight.W900,
                    color = Color.White,
                    fontSize = 24.sp
                )

                var checkedSound by remember { mutableStateOf(sharPreference.getSoundEnabled()) }
                CustomSwitch(
                    checked = checkedSound,
                    onCheckedChange = {
                        checkedSound = !checkedSound
                        sharPreference.saveSoundEnabled(checkedSound)
                    },
                    colors = CustomSwitchDefaults.colors(
                        checkedThumbColor = Brown,
                        uncheckedThumbColor = Brown
                    ),
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                )
            }

            DifficultySwitcher(
                selected = isHard,
                onSelectedChange = {
                    sharPreference.saveDifficult(it)
                    isHard = it
                },
                modifier = Modifier.padding(16.dp)
            )

        }
    }
}

@Composable
fun DifficultySwitcher(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColor = Color(0xFF4285F4)
    val unselectedColor = Brown

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color.Transparent)
            .height(40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
                .background(if (!selected) selectedColor else unselectedColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onSelectedChange(false) }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Easy",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp))
                .background(if (selected) selectedColor else unselectedColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onSelectedChange(true) }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hard",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        }
    }
}


@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SwitchColor = CustomSwitchDefaults.colors()
) {
    val thumbHeight = 24.dp
    val thumbWidth = 32.dp
    val trackWidth = 60.dp

    val animatedOffset by animateDpAsState(
        targetValue = if (checked) (trackWidth - thumbWidth) else 4.dp, label = ""
    )

    Box(
        modifier = modifier
            .size(width = trackWidth, height = thumbHeight)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = Pink,
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = enabled,
                onClick = { onCheckedChange(!checked) }
            )
            .shadow(elevation = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(thumbHeight)
                .offset(x = animatedOffset)
                .align(Alignment.TopStart)
                .background(
                    color = if (checked) colors.checkedThumbColor else colors.uncheckedThumbColor,
                    shape = RoundedCornerShape(50)
                )
        )
    }
}


object CustomSwitchDefaults {
    @Composable
    fun colors(
        checkedThumbColor: Color = Color.White,
        uncheckedThumbColor: Color = Color.White,
        checkedTrackColor: Color = Color(0xFF4285F4),
        uncheckedTrackColor: Color = Color(0xFF9E9E9E),
        checkedBorderColor: Color = Color.Transparent,
        uncheckedBorderColor: Color = Color.LightGray
    ): SwitchColor {
        return SwitchColor(
            checkedThumbColor = checkedThumbColor,
            uncheckedThumbColor = uncheckedThumbColor,
            checkedTrackColor = checkedTrackColor,
            uncheckedTrackColor = uncheckedTrackColor,
            checkedBorderColor = checkedBorderColor,
            uncheckedBorderColor = uncheckedBorderColor
        )
    }
}

data class SwitchColor(
    val checkedThumbColor: Color,
    val uncheckedThumbColor: Color,
    val checkedTrackColor: Color,
    val uncheckedTrackColor: Color,
    val checkedBorderColor: Color,
    val uncheckedBorderColor: Color
)
