package com.example.simplecalculator.domain.use_case

interface ExpressionCalculator {
    fun calculate(expression: String): Double
}