package com.example.simplecalculator.data.remote.repository.api

import com.example.simplecalculator.data.local.LocalCurrenciesDataSource
import com.example.simplecalculator.data.remote.api.BeaconApi
import com.example.simplecalculator.data.remote.dto.ConvertedCurrencyDto
import com.example.simplecalculator.data.remote.dto.CurrenciesDto
import com.example.simplecalculator.data.remote.dto.CurrencyInfoDto
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class FakeApi(
    private val shouldReturnError: Boolean = false
) : BeaconApi {
    override suspend fun getAvailableCurrencies(apiKey: String): Response<CurrenciesDto> =
        if (shouldReturnError) {
            Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "error"))
        } else {
            val currenciesDto = LocalCurrenciesDataSource.availableCurrencies.map { currencyInfo ->
                CurrencyInfoDto(
                    id = currencyInfo.id,
                    name = currencyInfo.name,
                    shortCode = currencyInfo.shortCode,
                    symbol = currencyInfo.symbol
                )
            }
                .let {
                    CurrenciesDto(response = it)
                }
            Response.success(currenciesDto)
        }

    override suspend fun convertToCurrency(
        apiKey: String,
        from: String,
        to: String,
        amount: Double
    ): Response<ConvertedCurrencyDto> =
        if (shouldReturnError) {
            Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "error"))
        } else {
            val convertedCurrencyDto = ConvertedCurrencyDto(
                value = LocalCurrenciesDataSource.convertedCurrency.value
            )
            Response.success(convertedCurrencyDto)
        }
}