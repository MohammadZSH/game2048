package com.test.classicsnake

import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.collections.get
import kotlin.math.abs


val BOARD_COLOR = Color(0xFF998b7e)
val EMPTY_TILE_COLOR = Color(0xFFbaad99)

val tileColors = listOf(
    Color(0xFFece4db),
    Color(0xFFe8d9ba),
    Color(0xFFe7b17b),
    Color(0xFFe9996c),
    Color(0xFFe86540),
    Color(0xFFecd277),
    Color(0xFFE7E31D),
)

@Composable
fun Game2048() {

    val viewModel: Game2048ViewModel = viewModel()
    val currentPointerCoordination by viewModel.currentPointerCoordination.collectAsState()
    val pxValue = LocalDensity.current.run { 32.dp.toPx() }
    val isLost by viewModel.isLost.collectAsState()
    Box(modifier = Modifier.pointerInteropFilter {
        when (it.action) {
            MotionEvent.ACTION_DOWN -> {
                viewModel.currentPointerCoordination.value = Pair(it.x, it.y)
            }

            MotionEvent.ACTION_UP -> {
                if (abs(currentPointerCoordination.first - it.x) > abs(currentPointerCoordination.second - it.y)) {
                    if (abs(currentPointerCoordination.first - it.x) > pxValue) {
                        if (it.x - currentPointerCoordination.first > 0) {
                            viewModel.moveRight("usage")
                        } else {
                            viewModel.moveLeft("usage")
                        }
                    }
                } else {
                    if (abs(currentPointerCoordination.second - it.y) > pxValue) {
                        if (it.y - currentPointerCoordination.second < 0) {
                            viewModel.moveUp("usage")
                        } else {
                            viewModel.moveDown("usage")
                        }
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {}
            else -> false
        }
        true
    }) {

        val cornerRadius = LocalDensity.current.run { 8.dp.toPx() }
        val tilesMargin = LocalDensity.current.run { 6.dp.toPx() }


        val grid by viewModel.grid.collectAsState()

        val textMeasurer = rememberTextMeasurer()
        val textStyle = TextStyle(
            color = Color.Black,
            fontSize = 48.sp,
            textAlign = TextAlign.Center
        )


        Canvas(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color(0xFFFAF8F1))
                .padding(8.dp)
        ) {

            val boardWidth = size.minDimension

            val tileSize = (boardWidth - 2 * tilesMargin) / viewModel.columnCount


            //draw board background
            drawRoundRect(
                color = BOARD_COLOR,
                size = Size(boardWidth, boardWidth),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )


            drawEmptyCells(viewModel, tileSize, tilesMargin, cornerRadius)


            for (i in 0 until viewModel.columnCount) {
                for (j in 0 until viewModel.columnCount) {
                    val indexOfChosenColor =
                        if (grid[i][j] == 2) 0 else if (grid[i][j] == 4) 1 else if (grid[i][j] == 8) 2 else if (grid[i][j] == 16) 3 else if (grid[i][j] == 32) 4 else if (grid[i][j] == 64) 5 else 6
                    if (grid[i][j] != 0) {
                        drawRoundRect(
                            topLeft = Offset(
                                j * tileSize + tilesMargin * 2,
                                i * tileSize + tilesMargin * 2
                            ),
                            color = tileColors[indexOfChosenColor],
                            size = Size(tileSize - 2 * tilesMargin, tileSize - 2 * tilesMargin),
                            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                        )


                        val textLayoutResult: TextLayoutResult =
                            textMeasurer.measure(
                                text = AnnotatedString(grid[i][j].toString()),
                                style = textStyle
                            )
                        val textSize = textLayoutResult.size

                        drawText(
                            textMeasurer = textMeasurer,
                            style = textStyle,
                            text = grid[i][j].toString(),
//                            size = Size(tileSize - 2 * tilesMargin, tileSize - 2 * tilesMargin),
                            topLeft = Offset(
                                (2 * j + 1) * tileSize / 2 + tilesMargin - textSize.width / 2,
                                (2 * i + 1) * tileSize / 2 + tilesMargin - textSize.height / 2
                            ),
                        )

                    }
                }
            }
        }
        AnimatedVisibility(isLost) {
            Box(Modifier
                .width(300.dp)
                .height(150.dp)
                .background(Color.Red)) {
                Text("U Lost!!!")
            }
        }
    }

}


private fun DrawScope.drawEmptyCells(
    viewModel: Game2048ViewModel,
    tileSize: Float,
    tilesMargin: Float,
    cornerRadius: Float
) {
    for (y in 0 until viewModel.columnCount) {
        for (x in 0 until viewModel.columnCount) {

            drawRoundRect(
                topLeft = Offset(x * tileSize + tilesMargin * 2, y * tileSize + tilesMargin * 2),
                color = EMPTY_TILE_COLOR,
                size = Size(tileSize - 2 * tilesMargin, tileSize - 2 * tilesMargin),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }
    }
}