package com.example.simplecalculator.ui

import com.example.simplecalculator.CalculatorAction

val compactLayoutButtons = listOf(
    listOf(
        Button(symbol = "AC", action = CalculatorAction.Clear, aspectRatio = 2F, weight = 2F),
        Button(symbol = "DEL", action = CalculatorAction.Delete),
        Button(symbol = "/", action = CalculatorAction.Append('/'))
    ),
    listOf(
        Button(symbol = "7", action = CalculatorAction.Append('7')),
        Button(symbol = "8", action = CalculatorAction.Append('8')),
        Button(symbol = "9", action = CalculatorAction.Append('9')),
        Button(symbol = "X", action = CalculatorAction.Append('X'))
    ),
    listOf(
        Button(symbol = "4", action = CalculatorAction.Append('4')),
        Button(symbol = "5", action = CalculatorAction.Append('5')),
        Button(symbol = "6", action = CalculatorAction.Append('6')),
        Button(symbol = "-", action = CalculatorAction.Append('-'))
    ),
    listOf(
        Button(symbol = "1", action = CalculatorAction.Append('1')),
        Button(symbol = "2", action = CalculatorAction.Append('2')),
        Button(symbol = "3", action = CalculatorAction.Append('3')),
        Button(symbol = "+", action = CalculatorAction.Append('+'))
    ),
    listOf(
        Button(symbol = "0", action = CalculatorAction.Append('0'), aspectRatio = 2F, weight = 2F),
        Button(symbol = ".", action = CalculatorAction.Append('.')),
        Button(symbol = "=", action = CalculatorAction.Calculate)
    )
)

val mediumLayoutButtons = listOf(
    listOf(
        Button(symbol = "AC", action = CalculatorAction.Clear, aspectRatio = 2F, weight = 2F),
        Button(symbol = "DEL", action = CalculatorAction.Delete),
    ),
    listOf(
        Button(symbol = "7", action = CalculatorAction.Append('7')),
        Button(symbol = "8", action = CalculatorAction.Append('8')),
        Button(symbol = "9", action = CalculatorAction.Append('9'))
    ),
    listOf(
        Button(symbol = "4", action = CalculatorAction.Append('4')),
        Button(symbol = "5", action = CalculatorAction.Append('5')),
        Button(symbol = "6", action = CalculatorAction.Append('6'))
    ),
    listOf(
        Button(symbol = "1", action = CalculatorAction.Append('1')),
        Button(symbol = "2", action = CalculatorAction.Append('2')),
        Button(symbol = "3", action = CalculatorAction.Append('3'))
    ),
    listOf(
        Button(symbol = "0", action = CalculatorAction.Append('0'), aspectRatio = 2F, weight = 2F),
        Button(symbol = ".", action = CalculatorAction.Append('.'))
    )
)

val mediumLayoutOperationButtons = listOf(
    Button(symbol = "/", action = CalculatorAction.Append('/')),
    Button(symbol = "X", action = CalculatorAction.Append('X')),
    Button(symbol = "-", action = CalculatorAction.Append('-')),
    Button(symbol = "+", action = CalculatorAction.Append('+')),
    Button(symbol = "=", action = CalculatorAction.Calculate)
)
