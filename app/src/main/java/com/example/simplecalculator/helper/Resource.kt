package com.example.simplecalculator.helper

import androidx.annotation.StringRes

sealed class Resource<T>(val data: T? = null, @StringRes val message: Int? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: Int, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}