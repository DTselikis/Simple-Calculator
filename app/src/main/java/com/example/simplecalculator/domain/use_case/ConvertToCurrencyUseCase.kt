package com.example.simplecalculator.domain.use_case

import com.example.simplecalculator.domain.model.Currency
import com.example.simplecalculator.domain.repository.CurrenciesRepository
import com.example.simplecalculator.helper.Resource

class ConvertToCurrencyUseCase(
    val currenciesRepository: CurrenciesRepository
) {

    suspend fun convertToCurrency(
        from: String,
        to: String,
        amount: String
    ): Resource<Currency> =
        currenciesRepository.convertToCurrency(from = from, to = to, amount = amount.toDouble())
}