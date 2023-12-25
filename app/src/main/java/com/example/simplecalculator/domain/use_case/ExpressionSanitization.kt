package com.example.simplecalculator.domain.use_case

interface ExpressionSanitization {

    fun sanitize(expression: String, input: Char): String
}