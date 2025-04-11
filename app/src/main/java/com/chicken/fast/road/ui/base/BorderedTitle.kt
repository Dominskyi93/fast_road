package com.chicken.fast.road.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chicken.fast.road.R
import com.chicken.fast.road.ui.theme.gradientBrush

@Composable
fun BorderedTitle(text: String, brush: Brush = gradientBrush, modifier: Modifier = Modifier) {

    Box(contentAlignment = Alignment.Center) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.jaro)),
            style = TextStyle(
                color = Color.White,
                letterSpacing = -2.sp,
                fontSize = 52.sp,
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.jaro)),
            modifier = modifier,
            style = TextStyle(
                brush = brush,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}