package com.test.classicsnake

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.update

@Composable
fun MainScreen(viewModel: ClassicSnakeViewModel, innerPadding: PaddingValues) {
    val snakeHead by viewModel.snakeHead.collectAsState()
    val foodOne by viewModel.foodOne.collectAsState()
    val foodTwo by viewModel.foodTwo.collectAsState()
    val foodThree by viewModel.foodThree.collectAsState()
    val foodFour by viewModel.foodFour.collectAsState()
    val foodFive by viewModel.foodFive.collectAsState()
    val snakeBodyCount by viewModel.snakeBodyCount.collectAsState()
    val snakeBodyPlacement = viewModel.snakeBodyHomePlacement
    val headIcon =
        if (snakeHead.currentMovementMethod == CurrentMovementMethod.RIGHT) painterResource(R.drawable.arrow_right)
        else if (snakeHead.currentMovementMethod == CurrentMovementMethod.LEFT) painterResource(R.drawable.arrow_left)
        else if (snakeHead.currentMovementMethod == CurrentMovementMethod.UP) painterResource(R.drawable.arrow_upward)
        else painterResource(R.drawable.arrow_downward)
    val isLoser by viewModel.isLoser.collectAsState()
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(viewModel.columnCount),
            ) {
                items((viewModel.columnCount) * (viewModel.rowCount)) {
                    Box(
                        Modifier
                            .size(40.dp)
                            .padding(1.dp)
                            .background(Color.Blue),
                        contentAlignment = Alignment.Center
                    ) {
                        if (
                            it + 1 == ((snakeHead.currentPosition.second * viewModel.columnCount) - (viewModel.columnCount - snakeHead.currentPosition.first))
                        ) {
                            Box(
                                Modifier
                                    .size(20.dp)
                                    .background(Color.Magenta)
                            ) {
                                Icon(
                                    painter = headIcon,
                                    contentDescription = null
                                )
                            }
                        }
                        if (snakeBodyPlacement.contains(it + 1)) {
                            Box(
                                Modifier
                                    .size(25.dp)
                                    .background(Color.Magenta)
                            )
                        }
                        if (it + 1 == foodOne) {
                            Box(
                                Modifier
                                    .size(15.dp)
                                    .background(Color.Cyan)
                            )
                        }
                        if (it + 1 == foodTwo) {
                            Box(
                                Modifier
                                    .size(15.dp)
                                    .background(Color.Red)
                            )
                        }
                        if (it + 1 == foodThree) {
                            Box(
                                Modifier
                                    .size(15.dp)
                                    .background(Color.Yellow)
                            )
                        }
                        if (it + 1 == foodFour) {
                            Box(
                                Modifier
                                    .size(15.dp)
                                    .background(Color.Black)
                            )
                        }
                        if (it + 1 == foodFive) {
                            Box(
                                Modifier
                                    .size(15.dp)
                                    .background(Color.White)
                            )
                        }

                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Cyan)
                        .clickable(
                            onClick =
                                {
                                    viewModel.snakeHead.update { it.copy(currentMovementMethod = CurrentMovementMethod.LEFT) }
                                },
                            enabled = if (snakeBodyCount <= 0) true else if (snakeHead.currentMovementMethod == CurrentMovementMethod.RIGHT) false else true,
                        ), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.weight(1f))
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = "",
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(Modifier.weight(1f))
                }

                Column(
                    modifier = Modifier
                        .background(Color.Red)
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    viewModel.snakeHead.update { it.copy(currentMovementMethod = CurrentMovementMethod.UP) }

                                },
                                enabled = if (snakeBodyCount <= 0) true else if (snakeHead.currentMovementMethod == CurrentMovementMethod.BOTTOM) false else true,
                            ), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.KeyboardArrowUp,
                            contentDescription = "",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    viewModel.snakeHead.update { it.copy(currentMovementMethod = CurrentMovementMethod.BOTTOM) }
                                },
                                enabled = if (snakeBodyCount <= 0) true else if (snakeHead.currentMovementMethod == CurrentMovementMethod.UP) false else true
                            ), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            modifier = Modifier.size(60.dp)
                        )
                    }


                }

                Column(
                    modifier = Modifier
                        .background(Color.Yellow)
                        .weight(1f), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        {
                            viewModel.snakeHead.update { it.copy(currentMovementMethod = CurrentMovementMethod.RIGHT) }

                        },
                        enabled = if (snakeBodyCount <= 0) true else if (snakeHead.currentMovementMethod == CurrentMovementMethod.LEFT) false else true,
                        modifier = Modifier.size(120.dp)
                    ) {
                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = "",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    Spacer(Modifier.weight(1f))

                }
            }
        }
        AnimatedVisibility(isLoser) {
            Box(
                Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .background(Color.Green, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("You Lost!!!")
            }
        }
    }


}