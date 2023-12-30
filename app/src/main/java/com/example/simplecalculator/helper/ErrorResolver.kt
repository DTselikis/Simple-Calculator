package com.example.simplecalculator.helper

import com.example.simplecalculator.R

object ErrorResolver {

    const val CONNECTIVITY_ERROR = 1
    const val CURRENCIES_NOT_FETCHED = 2

    val errorCodeToMessage = mapOf(
        CONNECTIVITY_ERROR to R.string.connectivity_error,
        CURRENCIES_NOT_FETCHED to R.string.available_currencies_fetch_failed
    )

}