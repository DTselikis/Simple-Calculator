package com.example.simplecalculator.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    val buttonHorizontalSpacing: Dp,
    val buttonVerticalSpacing: Dp
)

val smallDimensions = Dimensions(
    buttonHorizontalSpacing = 12.dp,
    buttonVerticalSpacing = 4.dp,
)

val largeDimensions = Dimensions(
    buttonHorizontalSpacing = 24.dp,
    buttonVerticalSpacing = 4.dp
)