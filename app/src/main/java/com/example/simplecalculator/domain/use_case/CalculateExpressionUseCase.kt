package com.example.simplecalculator.domain.use_case

import com.example.simplecalculator.consts.ADDITION_SYMBOL
import com.example.simplecalculator.consts.MULTIPLICATION_SYMBOL
import com.example.simplecalculator.consts.SUBTRACTION_SYMBOL
import com.example.simplecalculator.utils.hasPrecedence
import com.example.simplecalculator.utils.isDot
import java.util.Stack
import javax.inject.Inject

class CalculateExpressionUseCase @Inject constructor(): ExpressionCalculator {
    override fun calculate(expression: String): Double {
        val tokens = expression.split(OPERATIONS_REGEX.toRegex())
        val values = Stack<Double>()
        val operators = Stack<Char>()

        tokens.forEach { token ->
            if (token.all { it.isDigit() || it.isDot() }) {
                // If the token is a number, push it to the values stack
                values.push(token.toDouble())
            } else {
                // If the token is an operator, handle operator precedence
                while (operators.isNotEmpty() && token[0].hasPrecedence(operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()))
                }
                operators.push(token[0])
            }
        }

        while (operators.isNotEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()))
        }

        return values.pop()
    }

    private fun applyOperation(operator: Char, b: Double, a: Double) =
        when (operator) {
            ADDITION_SYMBOL -> a + b
            SUBTRACTION_SYMBOL -> a - b
            MULTIPLICATION_SYMBOL -> a * b
            else -> {
                if (b == 0.0) {
                    throw ArithmeticException("Cannot divide by zero")
                }
                a / b
            }
        }

    companion object {
        const val OPERATIONS_REGEX = "(?=[+\\-X/])|(?<=[+\\-X/])"
    }
}