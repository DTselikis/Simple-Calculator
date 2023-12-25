package com.example.simplecalculator.utils

fun Char.isDot() =
    this == '.'

fun Char.hasPrecedence(operator: Char):Boolean =
    ((operator == '+' || operator == '-') && (this == 'X' || this == '/')).not()