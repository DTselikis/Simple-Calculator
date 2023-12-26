package com.example.simplecalculator.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyInfoDto(
    val id: Int,
    val name: String,
    @SerialName("short_code")
    val shortCode: String,
    val symbol: String
)
