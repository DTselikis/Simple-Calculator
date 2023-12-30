package com.example.simplecalculator.data.local

import com.example.simplecalculator.domain.model.Currency
import com.example.simplecalculator.domain.model.CurrencyInfo

object LocalCurrenciesDataSource {
    val availableCurrencies = listOf(
        CurrencyInfo(
            id = 46,
            name = "Euro",
            shortCode = "EUR",
            symbol = "€"
        ),
        CurrencyInfo(
            id = 8,
            name = "Australian Dollar",
            shortCode = "AUD",
            symbol = "\$"
        ),
        CurrencyInfo(
            id = 18,
            name = "US Dollar",
            shortCode = "USD",
            symbol = "\$"
        )
    )

    val convertedCurrency = Currency(value = 5.0)
}
