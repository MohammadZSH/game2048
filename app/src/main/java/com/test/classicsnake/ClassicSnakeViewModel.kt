package com.test.classicsnake

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ClassicSnakeViewModel : ViewModel() {
    init {
        viewModelScope.launch {
            delay(2000)
            move()
        }
    }

    val columnCount = 10
    val rowCount = 15

    val snakeHead = MutableStateFlow(
        Snake(
            currentMovementMethod = CurrentMovementMethod.RIGHT,
            currentPosition = Pair(columnCount / 2, rowCount / 2)
        )
    )

    val snakeBody = mutableStateListOf<Snake>()
    val snakeBodyHomePlacement = mutableStateListOf<Int>()
    fun bodyManagement() {
        if (snakeBodyCount.value >= 1) {
            if (snakeBody.isNotEmpty()) {
                if (snakeBody.size == snakeBodyCount.value) {
                    if (snakeBody.size != 1) {
                        for (i in 1..(snakeBodyCount.value - 1)) {
                            snakeBody[snakeBodyCount.value - i] =
                                snakeBody[snakeBodyCount.value - i - 1]
                        }
                    }
                    snakeBody[0] = snakeHead.value
                } else {
                    snakeBody.add(
                        0,
                        snakeHead.value
                    )
                }
            } else {
                snakeBody.add(
                    0,
                    snakeHead.value
                )
            }
            if (snakeBodyHomePlacement.isNotEmpty()) {
                snakeBodyHomePlacement.removeAll { snakeBodyHomePlacement.isNotEmpty() }
            }
            snakeBody.forEach { snakeBodyHomePlacement.add(((it.currentPosition.second * columnCount) - (columnCount - it.currentPosition.first))) }
        }
    }

    var snakeBodyCount = MutableStateFlow(0)
    val listOfHomes =
        (1..(columnCount * rowCount)).toMutableList()
    val listOfAvailableHomes = mutableListOf<Int>()
    var foodOne = MutableStateFlow(0)
    var foodTwo = MutableStateFlow(0)
    var foodThree = MutableStateFlow(0)
    var foodFour = MutableStateFlow(0)
    var foodFive = MutableStateFlow(0)

    var isLoser = MutableStateFlow(false)
    fun move() {

        viewModelScope.launch {
            while (true) {
                if (!isLoser.value) {

                    listOfAvailableHomes.removeAll { listOfAvailableHomes.isNotEmpty() }
                    listOfHomes.forEach {
                        if (
                            it != ((snakeHead.value.currentPosition.second * columnCount) - (columnCount - snakeHead.value.currentPosition.first)) &&
                            !snakeBodyHomePlacement.contains(it)
                        ) {
                            listOfAvailableHomes.add(it)
                        }
                    }

                    try {
                        if (!listOfAvailableHomes.contains(foodOne.value) && foodOne.value != -1) {
                            if (foodOne.value != 0) {
                                snakeBodyCount.value = snakeBodyCount.value + 1
                            }
                            foodOne.value = listOfAvailableHomes.random()
                        }
                        listOfAvailableHomes.remove(foodOne.value)
                    } catch (e: Exception) {
                        foodOne.value = -1
                    }

                    try {
                        if (!listOfAvailableHomes.contains(foodTwo.value) && foodTwo.value != -1) {
                            if (foodTwo.value != 0) {
                                snakeBodyCount.value = snakeBodyCount.value + 1
                            }
                            foodTwo.value = listOfAvailableHomes.random()
                        }
                        listOfAvailableHomes.remove(foodTwo.value)
                    } catch (e: Exception) {
                        foodTwo.value = -1
                    }

                    try {
                        if (!listOfAvailableHomes.contains(foodThree.value) && foodThree.value != -1) {
                            if (foodThree.value != 0) {
                                snakeBodyCount.value = snakeBodyCount.value + 1
                            }
                            foodThree.value = listOfAvailableHomes.random()
                        }
                        listOfAvailableHomes.remove(foodThree.value)
                    } catch (e: Exception) {
                        foodThree.value = -1
                    }

                    try {
                        if (!listOfAvailableHomes.contains(foodFour.value) && foodFour.value != -1) {
                            if (foodFour.value != 0) {
                                snakeBodyCount.value = snakeBodyCount.value + 1
                            }
                            foodFour.value = listOfAvailableHomes.random()
                        }
                        listOfAvailableHomes.remove(foodFour.value)
                    } catch (e: Exception) {
                        foodFour.value = -1
                    }

                    try {
                        if (!listOfAvailableHomes.contains(foodFive.value) && foodFive.value != -1) {
                            if (foodFive.value != 0) {
                                snakeBodyCount.value = snakeBodyCount.value + 1
                            }
                            foodFive.value = listOfAvailableHomes.random()
                        }
                        listOfAvailableHomes.remove(foodFive.value)
                    } catch (e: Exception) {
                        foodFive.value = -1
                    }

                    bodyManagement()

                    if (snakeHead.value.currentMovementMethod == CurrentMovementMethod.RIGHT) {
                        if (snakeHead.value.currentPosition.first != columnCount) {
                            snakeHead.update {
                                it.copy(
                                    currentPosition = Pair(
                                        it.currentPosition.first + 1,
                                        it.currentPosition.second
                                    )
                                )
                            }
                        } else {
                            snakeHead.update {
                                it.copy(
                                    currentPosition = Pair(
                                        1,
                                        it.currentPosition.second
                                    )
                                )
                            }
                        }
                    } else if (snakeHead.value.currentMovementMethod == CurrentMovementMethod.LEFT) {
                        if (snakeHead.value.currentPosition.first != 1) {
                            snakeHead.update {
                                it.copy(
                                    currentPosition = Pair(
                                        it.currentPosition.first - 1,
                                        it.currentPosition.second
                                    )
                                )
                            }
                        } else {
                            snakeHead.update {
                                it.copy(
                                    currentPosition = Pair(
                                        columnCount,
                                        it.currentPosition.second
                                    )
                                )
                            }
                        }
                    } else if (snakeHead.value.currentMovementMethod == CurrentMovementMethod.UP) {
                        if (snakeHead.value.currentPosition.second != 1) {
                            snakeHead.update {
                                it.copy(
                                    currentPosition = Pair(
                                        it.currentPosition.first,
                                        it.currentPosition.second - 1
                                    )
                                )
                            }
                        } else {
                            snakeHead.update {
                                it.copy(
                                    currentPosition = Pair(
                                        it.currentPosition.first,
                                        rowCount
                                    )
                                )
                            }
                        }
                    } else {
                        if (snakeHead.value.currentPosition.second != rowCount) {
                            snakeHead.update {
                                it.copy(
                                    currentPosition = Pair(
                                        it.currentPosition.first,
                                        it.currentPosition.second + 1
                                    )
                                )
                            }
                        } else {
                            snakeHead.update {
                                it.copy(
                                    currentPosition = Pair(
                                        it.currentPosition.first,
                                        1
                                    )
                                )
                            }
                        }
                    }
                    if (snakeBodyHomePlacement.contains(((snakeHead.value.currentPosition.second * columnCount) - (columnCount - snakeHead.value.currentPosition.first)))) {
                        isLoser.value = true
                        snakeHead.value = Snake(
                            currentMovementMethod = CurrentMovementMethod.RIGHT,
                            currentPosition = Pair(columnCount / 2, rowCount / 2)
                        )
                        snakeBody.removeAll {snakeBody.isNotEmpty()}
                        snakeBodyCount.value = 0
                        snakeBodyHomePlacement.removeAll{snakeBodyHomePlacement.isNotEmpty()}

                    }
                    delay(500)
                } else {
                    delay(5000)
                    isLoser.value = false
                }
            }
        }

    }
}