package com.example.simplecalculator.ui

import com.example.simplecalculator.domain.model.CurrencyInfo

data class CurrencyConverterUiState(
    val availableCurrencies: List<CurrencyInfo> = emptyList(),
    val selectedCurrencyId: Int? = null,
    val convertedResult: String = "",
    val expanded: Boolean = false
)