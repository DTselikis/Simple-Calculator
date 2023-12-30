package com.example.simplecalculator.domain.use_case

import com.example.simplecalculator.utils.endsWithOperationSymbolOrDecimalPoint
import com.example.simplecalculator.utils.endsWithZero
import com.example.simplecalculator.utils.isDecimalPoint
import com.example.simplecalculator.utils.lastNumberContainsDecimalPoint

class SanitizeExpressionUseCase : ExpressionSanitization {
    override fun sanitize(expression: String, input: Char): String =
        if (input.isDigit()) {
            handleDigit(expression, input)
        } else if (input.isDecimalPoint()) {
            handleDecimalPoint(expression, input)
        } else {
            handleOperationSymbol(expression, input)
        }

    private fun handleDigit(expression: String, input: Char) =
        if (expression.length == 1 && expression.endsWithZero()) {
            input.toString()
        } else {
            expression + input
        }

    private fun handleDecimalPoint(expression: String, input: Char) =
        if (expression.lastNumberContainsDecimalPoint() || expression.endsWithOperationSymbolOrDecimalPoint()) {
            expression
        } else {
            expression + input
        }

    private fun handleOperationSymbol(expression: String, input: Char) =
        if (expression.endsWithOperationSymbolOrDecimalPoint()) {
            expression
        } else {
            expression + input
        }
}
