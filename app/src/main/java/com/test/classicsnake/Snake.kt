package com.test.classicsnake

import androidx.compose.foundation.layout.Arrangement

data class Snake(
    var currentMovementMethod: CurrentMovementMethod,
    var currentPosition: Pair<Int,Int>
)