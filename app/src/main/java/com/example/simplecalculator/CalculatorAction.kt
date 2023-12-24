package com.example.simplecalculator

sealed class CalculatorAction {
    data class Append(val symbol: String): CalculatorAction()
    data object Clear: CalculatorAction()
    data object Delete: CalculatorAction()
    data object Calculate: CalculatorAction()
}