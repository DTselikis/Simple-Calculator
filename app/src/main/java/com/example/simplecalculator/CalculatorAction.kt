package com.example.simplecalculator

sealed class CalculatorAction {
    data class Append(val symbol: Char): CalculatorAction()
    data object Clear: CalculatorAction()
    data object Delete: CalculatorAction()
    data object Calculate: CalculatorAction()
    data object ClearError: CalculatorAction()
    data class CurrencyChanged(val id: Int): CalculatorAction()
    data class ExpandedChanged(val expanded: Boolean): CalculatorAction()
}