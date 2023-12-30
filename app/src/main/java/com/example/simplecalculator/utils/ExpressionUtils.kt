package com.example.simplecalculator.utils

import com.example.simplecalculator.consts.ADDITION_SYMBOL
import com.example.simplecalculator.consts.DECIMAL_POINT
import com.example.simplecalculator.consts.DIVISION_SYMBOL
import com.example.simplecalculator.consts.MULTIPLICATION_SYMBOL
import com.example.simplecalculator.consts.SUBTRACTION_SYMBOL

fun String.lastNumberContainsDecimalPoint() =
    this.contains(NUMBER_WITH_DOT_REGEX.toRegex())

fun String.endsWithOperationSymbolOrDecimalPoint() =
    this.takeLast(1)[0].let {
        it == ADDITION_SYMBOL || it == SUBTRACTION_SYMBOL ||
                it == MULTIPLICATION_SYMBOL || it == DIVISION_SYMBOL ||
                it == DECIMAL_POINT
    }

fun String.endsWithZero() =
    this.takeLast(1) == "0"

fun String.notEndsWithZero() =
    this.endsWithZero().not()

fun String.endsWithDigit() =
    this.takeLast(1)[0].isDigit()

fun List<String>.replaceLastEmptyWithZero() =
    this.map { token ->
        token.ifEmpty { "0" }
    }

const val NUMBER_WITH_DOT_REGEX = "\\.\\d+$"