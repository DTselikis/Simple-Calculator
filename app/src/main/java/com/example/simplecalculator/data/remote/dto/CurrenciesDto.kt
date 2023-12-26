package com.example.simplecalculator.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesDto(
    val response: List<CurrencyInfoDto>
)
