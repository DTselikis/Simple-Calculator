package com.example.simplecalculator.data.remote.mapper

import com.example.simplecalculator.data.remote.dto.ConvertedCurrencyDto
import com.example.simplecalculator.data.remote.dto.CurrencyInfoDto
import com.example.simplecalculator.domain.model.Currency
import com.example.simplecalculator.domain.model.CurrencyInfo

fun ConvertedCurrencyDto.toCurrency() =
    Currency(value = this.value)

fun CurrencyInfoDto.toCurrencyInfo() =
    CurrencyInfo(
        id = this.id,
        name = this.name,
        shortCode = this.shortCode,
        symbol = this.symbol
    )
