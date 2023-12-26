package com.example.simplecalculator.domain.repository

import com.example.simplecalculator.domain.model.Currency
import com.example.simplecalculator.domain.model.CurrencyInfo
import com.example.simplecalculator.helper.Resource

interface CurrenciesRepository {

    suspend fun getAvailableCurrencies(): Resource<List<CurrencyInfo>>

    suspend fun convertToCurrency(
        from: String,
        to: String,
        amount: Double
    ): Resource<Currency>
}
