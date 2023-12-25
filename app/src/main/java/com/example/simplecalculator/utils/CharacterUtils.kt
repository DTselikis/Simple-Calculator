package com.example.simplecalculator.utils

import com.example.simplecalculator.consts.DECIMAL_POINT

fun Char.isDot() =
    this == DECIMAL_POINT

fun Char.hasPrecedence(operator: Char): Boolean =
    ((operator == '+' || operator == '-') && (this == 'X' || this == '/')).not()