package com.example.simplecalculator.utils

import com.example.simplecalculator.consts.ADDITION_SYMBOL
import com.example.simplecalculator.consts.DECIMAL_POINT
import com.example.simplecalculator.consts.DIVISION_SYMBOL
import com.example.simplecalculator.consts.MULTIPLICATION_SYMBOL
import com.example.simplecalculator.consts.SUBTRACTION_SYMBOL

fun Char.isDecimalPoint() =
    this == DECIMAL_POINT

fun Char.isOperationSymbol() =
    "${ADDITION_SYMBOL}${SUBTRACTION_SYMBOL}${MULTIPLICATION_SYMBOL}${DIVISION_SYMBOL}".contains(this)

fun Char.hasNotPrecedence(operator: Char): Boolean =
    ((operator == '+' || operator == '-') && (this == 'X' || this == '/')).not()