package com.example.simplecalculator.ui

import androidx.annotation.StringRes

data class SimpleCalculatorUiState(
    val input: String = "0",
    val result: String = "0",
    val currencyConverterUiState: CurrencyConverterUiState = CurrencyConverterUiState(),
    @StringRes val errorMessage: Int? = null
)
