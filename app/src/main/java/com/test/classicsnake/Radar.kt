package com.test.classicsnake

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Radar() {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val width = displayMetrics.widthPixels
    val height = displayMetrics.heightPixels
    val radius = width / 2f * 0.9f
    var angle by remember { mutableFloatStateOf(0f) }
    val angleInRodion = (angle * PI / 180).toFloat()
    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            angle++
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(radius = radius, center = Offset(width / 2f, height / 2f), color = Color.Red)
        drawLine(
            color = Color.Cyan,
            start = Offset(width / 2f, height / 2f),
            end = Offset(
                width / 2 + radius * cos(angleInRodion),
                height / 2 - radius * sin(angleInRodion)
            ), strokeWidth = 10f
        )
    }
}