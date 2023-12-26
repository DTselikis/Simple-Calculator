package com.example.simplecalculator.data.remote.api

import com.example.simplecalculator.BuildConfig
import com.example.simplecalculator.data.remote.dto.ConvertedCurrencyDto
import com.example.simplecalculator.data.remote.dto.CurrenciesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeaconApi {

    @GET("currencies")
    suspend fun getAvailableCurrencies(
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<CurrenciesDto>

    @GET("convert")
    suspend fun convertToCurrency(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<ConvertedCurrencyDto>

    companion object {
        const val BASE_URL = "https://api.currencybeacon.com/v1/"
        const val API_KEY = BuildConfig.API_KEY
    }
}
