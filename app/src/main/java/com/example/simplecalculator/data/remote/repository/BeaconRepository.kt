package com.example.simplecalculator.data.remote.repository

import com.example.simplecalculator.R
import com.example.simplecalculator.data.remote.api.BeaconApi
import com.example.simplecalculator.data.remote.mapper.toCurrency
import com.example.simplecalculator.data.remote.mapper.toCurrencyInfo
import com.example.simplecalculator.domain.model.Currency
import com.example.simplecalculator.domain.model.CurrencyInfo
import com.example.simplecalculator.domain.repository.CurrenciesRepository
import com.example.simplecalculator.helper.ErrorResolver
import com.example.simplecalculator.helper.Resource
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BeaconRepository(
    private val api: BeaconApi,
    private val ioDispatcher: CoroutineContext
) : CurrenciesRepository {
    override suspend fun getAvailableCurrencies(): Resource<List<CurrencyInfo>> =
        withContext(ioDispatcher) {
            try {
                api.getAvailableCurrencies().let { response ->
                    if (response.isSuccessful) {
                        response.body()!!.response
                            .map { currencyInfoDto ->
                                currencyInfoDto.toCurrencyInfo()
                            }
                            .let { currencyInfos ->
                                Resource.Success(currencyInfos)
                            }
                    } else {
                        Resource.Error(ErrorResolver.CONNECTIVITY_ERROR)
                    }
                }
            } catch (e: Exception) {
                Resource.Error(R.string.connectivity_error)
            }
        }

    override suspend fun convertToCurrency(
        from: String,
        to: String,
        amount: Double
    ): Resource<Currency> =
        withContext(ioDispatcher) {
            try {
                api.convertToCurrency(
                    from = from,
                    to = to,
                    amount = amount
                ).let { response ->
                    if (response.isSuccessful) {
                        Resource.Success(response.body()!!.toCurrency())
                    } else {
                        Resource.Error(ErrorResolver.CONNECTIVITY_ERROR)
                    }
                }
            } catch (e: Exception) {
                Resource.Error(ErrorResolver.CONNECTIVITY_ERROR)
            }
        }
}
