package com.example.simplecalculator.domain.use_case

import com.example.simplecalculator.domain.model.CurrencyInfo
import com.example.simplecalculator.domain.repository.CurrenciesRepository
import com.example.simplecalculator.helper.Resource

class GetAvailableCurrenciesUseCase(
    val currenciesRepository: CurrenciesRepository
) {

    suspend fun getAvailableCurrencies(): Resource<List<CurrencyInfo>> =
        currenciesRepository.getAvailableCurrencies()
}