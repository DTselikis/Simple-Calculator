package com.example.simplecalculator.ui

data class SimpleCalculatorUiState(
    val input: String = "0",
    val result: String = "0",
    val currencyConverterUiState: CurrencyConverterUiState = CurrencyConverterUiState()
)
