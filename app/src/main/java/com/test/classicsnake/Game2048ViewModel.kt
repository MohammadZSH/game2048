package com.test.classicsnake

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.classicsnake.utils.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Game2048ViewModel : ViewModel() {

    val columnCount = 4
    val isLost = MutableStateFlow(false)
    val isLostConditions = mutableStateListOf(false, false, false, false)
    fun moveUp(testOrUsage: String) {
        if (testOrUsage == "usage") {
            val tempGrid = MutableStateFlow(Array(columnCount) { IntArray(columnCount) })
            for (j in 0 until columnCount) {
                for (i in 0 until columnCount) {
                    tempGrid.value[j][i] = grid.value[j][i]
                }
            }
            repeat(3) {
                for (j in 1 until columnCount) {
                    for (i in 0 until columnCount) {
                        if (tempGrid.value[j][i] != 0) {
                            if (tempGrid.value[j - 1][i] == 0) {
                                tempGrid.value[j - 1][i] = tempGrid.value[j][i]
                                tempGrid.value[j][i] = 0
                            } else if (tempGrid.value[j][i] == tempGrid.value[j - 1][i]) {
                                tempGrid.value[j - 1][i] = tempGrid.value[j][i] * 2
                                tempGrid.value[j][i] = 0
                            }
                        }
                    }
                }
            }
            grid.value = tempGrid.value
            addNewTile()
        } else {
            val tempGrid = MutableStateFlow(Array(columnCount) { IntArray(columnCount) })
            for (j in 0 until columnCount) {
                for (i in 0 until columnCount) {
                    tempGrid.value[j][i] = grid.value[j][i]
                }
            }
            repeat(3) {
                for (j in 1 until columnCount) {
                    for (i in 0 until columnCount) {
                        if (tempGrid.value[j][i] != 0) {
                            if (tempGrid.value[j - 1][i] == 0) {
                                tempGrid.value[j - 1][i] = tempGrid.value[j][i]
                                tempGrid.value[j][i] = 0
                                isLostConditions[0] = false
                                return
                            } else if (tempGrid.value[j][i] == tempGrid.value[j - 1][i]) {
                                tempGrid.value[j - 1][i] = tempGrid.value[j][i] * 2
                                tempGrid.value[j][i] = 0
                                isLostConditions[0] = false
                                return
                            }
                        }
                    }
                }
            }
            isLostConditions[0] = true
        }
    }

    fun moveDown(testOrUsage: String) {
        if (testOrUsage == "usage") {
            val tempGrid = MutableStateFlow(Array(columnCount) { IntArray(columnCount) })
            for (j in 0 until columnCount) {
                for (i in 0 until columnCount) {
                    tempGrid.value[j][i] = grid.value[j][i]
                }
            }
            repeat(3) {
                for (j in columnCount - 2 downTo 0) {
                    for (i in 0 until columnCount) {
                        if (tempGrid.value[j][i] != 0) {
                            if (tempGrid.value[j + 1][i] == 0) {
                                tempGrid.value[j + 1][i] = tempGrid.value[j][i]
                                tempGrid.value[j][i] = 0
                            } else if (tempGrid.value[j][i] == tempGrid.value[j + 1][i]) {
                                tempGrid.value[j + 1][i] = tempGrid.value[j][i] * 2
                                tempGrid.value[j][i] = 0
                            }
                        }
                    }
                }
            }
            grid.value = tempGrid.value
            addNewTile()
        } else {
            val tempGrid = MutableStateFlow(Array(columnCount) { IntArray(columnCount) })
            for (j in 0 until columnCount) {
                for (i in 0 until columnCount) {
                    tempGrid.value[j][i] = grid.value[j][i]
                }
            }
            repeat(3) {
                for (j in columnCount - 2 downTo 0) {
                    for (i in 0 until columnCount) {
                        if (tempGrid.value[j][i] != 0) {
                            if (tempGrid.value[j + 1][i] == 0) {
                                tempGrid.value[j + 1][i] = tempGrid.value[j][i]
                                tempGrid.value[j][i] = 0
                                isLostConditions[1] = false
                                return
                            } else if (tempGrid.value[j][i] == tempGrid.value[j + 1][i]) {
                                tempGrid.value[j + 1][i] = tempGrid.value[j][i] * 2
                                tempGrid.value[j][i] = 0
                                isLostConditions[1] = false
                                return
                            }
                        }
                    }
                }
            }
            isLostConditions[1] = true
        }
    }

    fun moveRight(testOrUsage: String) {
        if (testOrUsage == "usage") {
            val tempGrid = MutableStateFlow(Array(columnCount) { IntArray(columnCount) })
            for (j in 0 until columnCount) {
                for (i in 0 until columnCount) {
                    tempGrid.value[j][i] = grid.value[j][i]
                }
            }
            repeat(3) {
                for (j in 0 until columnCount) {
                    for (i in columnCount - 2 downTo 0) {
                        if (tempGrid.value[j][i] != 0) {
                            if (tempGrid.value[j][i + 1] == 0) {
                                tempGrid.value[j][i + 1] = tempGrid.value[j][i]
                                tempGrid.value[j][i] = 0
                            } else if (tempGrid.value[j][i] == tempGrid.value[j][i + 1]) {
                                tempGrid.value[j][i + 1] = tempGrid.value[j][i] * 2
                                tempGrid.value[j][i] = 0
                            }
                        }
                    }
                }
            }
            grid.value = tempGrid.value
            addNewTile()
        } else {
            val tempGrid = MutableStateFlow(Array(columnCount) { IntArray(columnCount) })
            for (j in 0 until columnCount) {
                for (i in 0 until columnCount) {
                    tempGrid.value[j][i] = grid.value[j][i]
                }
            }
            repeat(3) {
                for (j in 0 until columnCount) {
                    for (i in columnCount - 2 downTo 0) {
                        if (tempGrid.value[j][i] != 0) {
                            if (tempGrid.value[j][i + 1] == 0) {
                                tempGrid.value[j][i + 1] = tempGrid.value[j][i]
                                tempGrid.value[j][i] = 0
                                isLostConditions[2] = false
                                return
                            } else if (tempGrid.value[j][i] == tempGrid.value[j][i + 1]) {
                                tempGrid.value[j][i + 1] = tempGrid.value[j][i] * 2
                                tempGrid.value[j][i] = 0
                                isLostConditions[2] = false
                                return
                            }
                        }
                    }
                }
            }
            isLostConditions[2] = true
        }
    }

    fun moveLeft(testOrUsage: String) {
        if (testOrUsage == "usage") {
            val tempGrid = MutableStateFlow(Array(columnCount) { IntArray(columnCount) })
            for (j in 0 until columnCount) {
                for (i in 0 until columnCount) {
                    tempGrid.value[j][i] = grid.value[j][i]
                }
            }
            repeat(3) {
                for (j in 0 until columnCount) {
                    for (i in 1 until columnCount) {
                        if (tempGrid.value[j][i] != 0) {
                            if (tempGrid.value[j][i - 1] == 0) {
                                tempGrid.value[j][i - 1] = tempGrid.value[j][i]
                                tempGrid.value[j][i] = 0
                            } else if (tempGrid.value[j][i] == tempGrid.value[j][i - 1]) {
                                tempGrid.value[j][i - 1] = tempGrid.value[j][i] * 2
                                tempGrid.value[j][i] = 0
                            }
                        }
                    }
                }
            }
            grid.value = tempGrid.value
            addNewTile()
        } else {
            val tempGrid = MutableStateFlow(Array(columnCount) { IntArray(columnCount) })
            for (j in 0 until columnCount) {
                for (i in 0 until columnCount) {
                    tempGrid.value[j][i] = grid.value[j][i]
                }
            }
            repeat(3) {
                for (j in 0 until columnCount) {
                    for (i in 1 until columnCount) {
                        if (tempGrid.value[j][i] != 0) {
                            if (tempGrid.value[j][i - 1] == 0) {
                                tempGrid.value[j][i - 1] = tempGrid.value[j][i]
                                tempGrid.value[j][i] = 0
                                isLostConditions[3] = false
                                return
                            } else if (tempGrid.value[j][i] == tempGrid.value[j][i - 1]) {
                                tempGrid.value[j][i - 1] = tempGrid.value[j][i] * 2
                                tempGrid.value[j][i] = 0
                                isLostConditions[3] = false
                                return
                            }
                        }
                    }
                }
            }
            isLostConditions[3] = true
        }
    }

    val currentPointerCoordination = MutableStateFlow(Pair(0f, 0f))
    val grid = MutableStateFlow(Array(columnCount) { IntArray(columnCount) })

    //0, 0 , 0 ,0
    //0, 0 , 0 ,0
    //0, 0 , 0 ,0
    //0, 0 , 0 ,0

    init {
        addNewTile()
        addNewTile()
        isLost()
    }

    private fun isLost() {
        viewModelScope.launch {
            while (true) {
                delay(2000)
                moveUp("test")
                moveDown("test")
                moveLeft("test")
                moveRight("test")
                if (isLostConditions[0] && isLostConditions[1] && isLostConditions[2] && isLostConditions[3]) {
                    isLost.value = true
                    delay(5000)
                    isLost.value = false
                    for (j in 0 until columnCount) {
                        for (i in 0 until columnCount) {
                           grid.value[j][i] = 0
                        }
                    }
                    addNewTile()
                    addNewTile()
                }
                log("islost: ${isLost.value} ,\n" +
                        " isLostConditions[0]: ${isLostConditions[0]},\n" +
                        " isLostConditions[1]: ${isLostConditions[1]},\n" +
                        "  isLostConditions[2]: ${isLostConditions[2]},\n" +
                        " isLostConditions[3]: ${isLostConditions[3]}")
            }
        }
    }

    private fun addNewTile() {
        val newTilePosition = getRandomEmptyCell()

        if (newTilePosition == null) {
            //GAME is OVER
        } else {
            grid.value[newTilePosition.first][newTilePosition.second] = 2
        }
    }

    private fun getRandomEmptyCell(): Pair<Int, Int>? {
        val emptyCells = mutableListOf<Pair<Int, Int>>()

        for (x in 0 until columnCount) {
            for (y in 0 until columnCount) {
                if (grid.value[x][y] == 0) {
                    emptyCells.add(Pair(x, y))
                }
            }
        }

        return if (emptyCells.isNotEmpty()) {
            emptyCells.random()
        } else {
            null
        }

    }

    private fun logGrid() {
        val gridText = StringBuilder()

        gridText.append("game gird : \n")
//        gridText.appendLine()
        for (i in 0 until columnCount) {
            for (j in 0 until columnCount) {
                gridText.append("  ${grid.value[i][j]}  ")

            }
            gridText.appendLine()
        }

        log(gridText.toString())
    }
}